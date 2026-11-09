package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.repository.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideojuegoDAOImpl implements IVideojuegoDAO {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @Override
    public List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMax, Boolean soloDisponibles) {
        return videojuegoRepository.buscarConFiltros(titulo, idDesarrolladora, anioDesde, pegiMax, soloDisponibles);
    }

    @Override
    public void insertar(String titulo, int anioLanzamiento, int pegi, int idDesarrolladora) {
        videojuegoRepository.insertar(titulo, anioLanzamiento, pegi, idDesarrolladora);
    }

    @Override
    public int contarTotal() {
        return videojuegoRepository.contarTotal();
    }
}