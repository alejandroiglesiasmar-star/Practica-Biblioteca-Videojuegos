package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.DesarrolladoraDTO;
import java.util.List;

public interface IDesarrolladoraDAO {
    List<DesarrolladoraDTO> obtenerTodas();
}