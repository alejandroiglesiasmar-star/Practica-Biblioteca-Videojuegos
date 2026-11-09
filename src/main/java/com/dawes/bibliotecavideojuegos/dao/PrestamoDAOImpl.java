package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PrestamoDAOImpl implements PrestamoDAO {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<PrestamoDTO> buscarConFiltros(String estado, LocalDate fechaDesde) {
        return prestamoRepository.buscarConFiltros(estado, fechaDesde);
    }

    @Override
    public PrestamoDTO obtenerPorId(Integer idPrestamo) {
        return prestamoRepository.obtenerPorId(idPrestamo);
    }

    @Override
    public Integer insertar(PrestamoDTO prestamo) {
        return prestamoRepository.insertar(prestamo);
    }

    @Override
    public void registrarDevolucion(Integer idPrestamo, LocalDate fechaDevolucion, double multa) {
        prestamoRepository.registrarDevolucion(idPrestamo, fechaDevolucion, multa);
    }

    @Override
    public int contarAbiertos() {
        return prestamoRepository.contarAbiertos();
    }

    @Override
    public int contarVencidos() {
        return prestamoRepository.contarVencidos();
    }

    @Override
    public int contarAbiertosPorSocio(Integer idSocio) {
        return prestamoRepository.contarAbiertosPorSocio(idSocio);
    }

    @Override
    public boolean tieneVencidosSinDevolver(Integer idSocio) {
        return prestamoRepository.tieneVencidosSinDevolver(idSocio);
    }
}