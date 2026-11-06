package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.PrestamoDAO;
import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoDAO prestamoDAO;

    public List<PrestamoDTO> buscarTodos() {
        return prestamoDAO.buscarTodos();
    }

    public void guardarPrestamo(PrestamoDTO prestamo) {
        prestamoDAO.guardar(prestamo);
    }
}