package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.CopiaDTO;

import java.util.List;

public interface ICopiaDAO {
    List<CopiaDTO> obtenerDisponibles();
    CopiaDTO obtenerPorId(Integer idCopia);
    boolean esDisponible(Integer idCopia);
    void marcarDanada(Integer idCopia);
}
