package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideojuegoService {

    @Autowired
    private IVideojuegoDAO videojuegoDAO;

    public List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMax) {
        return videojuegoDAO.buscarConFiltros(titulo, idDesarrolladora, anioDesde, pegiMax);
    }

    public void insertarVideojuego(String titulo, int anioLanzamiento, int pegi, int idDesarrolladora) {
        videojuegoDAO.insertar(titulo, anioLanzamiento, pegi, idDesarrolladora);
    }

    public int contarTotal() {
        return videojuegoDAO.contarTotal();
    }
}