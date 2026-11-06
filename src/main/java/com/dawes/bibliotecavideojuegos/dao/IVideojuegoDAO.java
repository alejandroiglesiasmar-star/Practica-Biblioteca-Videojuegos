package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import java.util.List;

public interface IVideojuegoDAO {
    List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMax);
    int insertar(String titulo, int anioLanzamiento, int pegi, int idDesarrolladora);

    // Método necesario para los contadores de la página de inicio (RF-08)
    int contarTotal();
}