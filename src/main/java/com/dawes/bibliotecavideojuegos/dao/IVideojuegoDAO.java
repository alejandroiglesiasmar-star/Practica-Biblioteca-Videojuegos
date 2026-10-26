package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import java.util.List;

public interface IVideojuegoDAO {
    List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, Boolean soloDisponibles);
    int insertar(String titulo, int anioLanzamiento, int pegi, int idDesarrolladora);
}