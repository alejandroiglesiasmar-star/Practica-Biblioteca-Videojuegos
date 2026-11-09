package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;

import java.util.List;

public interface SocioDAO {
    List<SocioDTO> buscarConFiltros(String nombreApellidos, String email, Integer activo);
    SocioDTO obtenerPorId(Integer idSocio);
    List<SocioDTO> obtenerActivos();
    boolean existeEmail(String email);
    void insertar(SocioDTO socio);
    int contarActivos();
}
