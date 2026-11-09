package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoDAO {
    List<PrestamoDTO> buscarConFiltros(String estado, LocalDate fechaDesde);
    PrestamoDTO obtenerPorId(Integer idPrestamo);
    Integer insertar(PrestamoDTO prestamo);
    void registrarDevolucion(Integer idPrestamo, LocalDate fechaDevolucion, double multa);
    int contarAbiertos();
    int contarVencidos();
    int contarAbiertosPorSocio(Integer idSocio);
    boolean tieneVencidosSinDevolver(Integer idSocio);
}