package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import java.util.List;

public interface PrestamoDAO {
    List<PrestamoDTO> buscarTodos();
    void guardar(PrestamoDTO prestamo);
    int contarAbiertos();
    int contarVencidos();
}