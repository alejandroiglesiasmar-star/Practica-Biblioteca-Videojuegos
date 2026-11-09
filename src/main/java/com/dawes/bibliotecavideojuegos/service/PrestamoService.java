package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.ICopiaDAO;
import com.dawes.bibliotecavideojuegos.dao.PrestamoDAO;
import com.dawes.bibliotecavideojuegos.dao.SocioDAO;
import com.dawes.bibliotecavideojuegos.dto.CopiaDTO;
import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import com.dawes.bibliotecavideojuegos.exception.ReglaNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PrestamoService {

    private static final int MAX_PRESTAMOS_ABIERTOS = 3;
    private static final int DIAS_NOVEDAD = 7;
    private static final int DIAS_RESTO = 15;
    private static final double MULTA_POR_DIA = 0.50;

    @Autowired
    private PrestamoDAO prestamoDAO;

    @Autowired
    private SocioDAO socioDAO;

    @Autowired
    private ICopiaDAO copiaDAO;

    public List<PrestamoDTO> buscarConFiltros(String estado, LocalDate fechaDesde) {
        List<PrestamoDTO> prestamos = prestamoDAO.buscarConFiltros(estado, fechaDesde);
        LocalDate hoy = LocalDate.now();
        for (PrestamoDTO prestamo : prestamos) {
            calcularEstadoYRetraso(prestamo, hoy);
        }
        return prestamos;
    }

    private void calcularEstadoYRetraso(PrestamoDTO prestamo, LocalDate hoy) {
        if (prestamo.getFechaDevolucion() != null) {
            prestamo.setEstado("DEVUELTO");
            long dias = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), prestamo.getFechaDevolucion());
            prestamo.setDiasRetraso(dias > 0 ? (int) dias : 0);
        } else if (prestamo.getFechaLimite().isBefore(hoy)) {
            prestamo.setEstado("VENCIDO");
            prestamo.setDiasRetraso((int) ChronoUnit.DAYS.between(prestamo.getFechaLimite(), hoy));
        } else {
            prestamo.setEstado("EN PLAZO");
            prestamo.setDiasRetraso(0);
        }
    }

    public List<SocioDTO> obtenerSociosActivos() {
        return socioDAO.obtenerActivos();
    }

    public List<CopiaDTO> obtenerCopiasDisponibles() {
        return copiaDAO.obtenerDisponibles();
    }

    @Transactional
    public PrestamoDTO registrarPrestamo(Integer idSocio, Integer idCopia) {
        SocioDTO socio = socioDAO.obtenerPorId(idSocio);
        CopiaDTO copia = copiaDAO.obtenerPorId(idCopia);

        // RN-1: el socio debe estar activo
        if (socio == null || !Boolean.TRUE.equals(socio.getActivo())) {
            throw new ReglaNegocioException("RN-1: El socio no está activo o no existe.");
        }

        // RN-2: la copia debe estar disponible (activa y sin préstamo abierto)
        if (copia == null || !copiaDAO.esDisponible(idCopia)) {
            throw new ReglaNegocioException("RN-2: La copia seleccionada no está disponible.");
        }

        // RN-3: máximo 3 préstamos abiertos por socio
        if (prestamoDAO.contarAbiertosPorSocio(idSocio) >= MAX_PRESTAMOS_ABIERTOS) {
            throw new ReglaNegocioException("RN-3: El socio ya tiene 3 préstamos abiertos.");
        }

        // RN-4: la edad del socio debe ser mayor o igual que el PEGI del juego
        if (socio.getFechaNacimiento() != null && copia.getPegi() != null) {
            int edad = Period.between(socio.getFechaNacimiento(), LocalDate.now()).getYears();
            if (edad < copia.getPegi()) {
                throw new ReglaNegocioException("RN-4: El socio (" + edad + " años) no alcanza el PEGI " + copia.getPegi() + " del juego.");
            }
        }

        // RN-5: no puede tener préstamos vencidos sin devolver
        if (prestamoDAO.tieneVencidosSinDevolver(idSocio)) {
            throw new ReglaNegocioException("RN-5: El socio tiene préstamos vencidos sin devolver.");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = calcularFechaLimite(hoy, copia.getAnioLanzamiento());

        PrestamoDTO prestamo = new PrestamoDTO();
        prestamo.setIdSocio(idSocio);
        prestamo.setIdCopia(idCopia);
        prestamo.setFechaPrestamo(hoy);
        prestamo.setFechaLimite(fechaLimite);
        prestamo.setMulta(0.0);

        Integer idPrestamo = prestamoDAO.insertar(prestamo);
        prestamo.setId(idPrestamo);
        return prestamoDAO.obtenerPorId(idPrestamo);
    }

    private LocalDate calcularFechaLimite(LocalDate fechaPrestamo, Integer anioLanzamiento) {
        int anioActual = LocalDate.now().getYear();
        boolean esNovedad = anioLanzamiento != null && anioLanzamiento >= anioActual - 1;
        return fechaPrestamo.plusDays(esNovedad ? DIAS_NOVEDAD : DIAS_RESTO);
    }

    @Transactional
    public PrestamoDTO devolverPrestamo(Integer idPrestamo, boolean copiaDanada) {
        PrestamoDTO prestamo = prestamoDAO.obtenerPorId(idPrestamo);
        if (prestamo == null) {
            throw new ReglaNegocioException("El préstamo no existe.");
        }
        if (prestamo.getFechaDevolucion() != null) {
            throw new ReglaNegocioException("Este préstamo ya fue devuelto.");
        }

        LocalDate hoy = LocalDate.now();
        double multa = calcularMulta(hoy, prestamo.getFechaLimite());

        prestamoDAO.registrarDevolucion(idPrestamo, hoy, multa);

        if (copiaDanada) {
            copiaDAO.marcarDanada(prestamo.getIdCopia());
        }

        return prestamoDAO.obtenerPorId(idPrestamo);
    }

    private double calcularMulta(LocalDate fechaDevolucion, LocalDate fechaLimite) {
        long diasRetraso = ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);
        if (diasRetraso <= 0) {
            return 0.0;
        }
        return Math.round(diasRetraso * MULTA_POR_DIA * 100.0) / 100.0;
    }

    public int contarPrestamosAbiertos() {
        return prestamoDAO.contarAbiertos();
    }

    public int contarPrestamosVencidos() {
        return prestamoDAO.contarVencidos();
    }
}
