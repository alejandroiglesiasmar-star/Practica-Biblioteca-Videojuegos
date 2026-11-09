package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.SocioDAO;
import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import com.dawes.bibliotecavideojuegos.exception.ReglaNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SocioService {

    @Autowired
    private SocioDAO socioDAO;

    public List<SocioDTO> buscarConFiltros(String nombreApellidos, String email, Integer activo) {
        return socioDAO.buscarConFiltros(nombreApellidos, email, activo);
    }

    public List<SocioDTO> obtenerSociosActivos() {
        return socioDAO.obtenerActivos();
    }

    public SocioDTO obtenerPorId(Integer idSocio) {
        return socioDAO.obtenerPorId(idSocio);
    }

    public void insertarSocio(SocioDTO socio) {
        validar(socio);
        socio.setFechaAlta(LocalDate.now());
        socio.setActivo(true);
        socioDAO.insertar(socio);
    }

    private void validar(SocioDTO socio) {
        if (socio.getNombre() == null || socio.getNombre().trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre es obligatorio.");
        }
        if (socio.getApellidos() == null || socio.getApellidos().trim().isEmpty()) {
            throw new ReglaNegocioException("Los apellidos son obligatorios.");
        }
        if (socio.getEmail() == null || socio.getEmail().trim().isEmpty()) {
            throw new ReglaNegocioException("El email es obligatorio.");
        }
        if (socio.getFechaNacimiento() == null) {
            throw new ReglaNegocioException("La fecha de nacimiento es obligatoria.");
        }
        if (socio.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new ReglaNegocioException("La fecha de nacimiento no puede ser futura.");
        }
        if (socioDAO.existeEmail(socio.getEmail().trim())) {
            throw new ReglaNegocioException("Ya existe un socio con el email " + socio.getEmail().trim() + ".");
        }
    }

    public int contarSociosActivos() {
        return socioDAO.contarActivos();
    }
}