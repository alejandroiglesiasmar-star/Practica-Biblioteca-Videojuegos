package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.SocioDAO;
import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocioService {

    @Autowired
    private SocioDAO socioDAO;

    public List<SocioDTO> obtenerTodosLosSocios() {
        return socioDAO.buscarTodos();
    }

    public List<SocioDTO> buscarSocios(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return socioDAO.buscarTodos();
        }
        return socioDAO.buscarPorNombreODni(filtro.trim());
    }

    public SocioDTO obtenerSocioPorId(Integer idSocio) {
        return socioDAO.buscarPorId(idSocio);
    }

    public boolean guardarSocio(SocioDTO socio, StringBuilder mensajeError) {
        if (socio.getNombre() == null || socio.getNombre().trim().isEmpty()) {
            mensajeError.append("El nombre del socio es obligatorio.");
            return false;
        }
        if (socio.getApellidos() == null || socio.getApellidos().trim().isEmpty()) {
            mensajeError.append("Los apellidos del socio son obligatorios.");
            return false;
        }
        if (socio.getDni() == null || socio.getDni().trim().isEmpty()) {
            mensajeError.append("El DNI es obligatorio.");
            return false;
        }

        // Comprobar si ya existe un socio con ese DNI
        if (socioDAO.existeDni(socio.getDni())) {
            mensajeError.append("Ya existe un socio registrado con ese DNI.");
            return false;
        }

        int filas = socioDAO.insertar(socio);
        return filas > 0;
    }
}