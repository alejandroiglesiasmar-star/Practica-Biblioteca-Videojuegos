package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.SocioDAOImpl;
import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocioService {

    @Autowired
    private SocioDAOImpl socioDAO;

    public List<SocioDTO> buscarConFiltros(String nombreApellidos, String email, Integer activo) {
        return socioDAO.buscarConFiltros(nombreApellidos, email, activo);
    }

    public List<SocioDTO> obtenerTodosLosSocios() {
        return socioDAO.obtenerTodos();
    }
    public int contarSociosActivos() {
        return socioDAO.contarActivos();
    }
}