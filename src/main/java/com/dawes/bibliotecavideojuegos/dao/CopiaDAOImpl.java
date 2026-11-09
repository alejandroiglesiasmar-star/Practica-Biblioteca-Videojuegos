package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.CopiaDTO;
import com.dawes.bibliotecavideojuegos.repository.CopiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CopiaDAOImpl implements ICopiaDAO {

    @Autowired
    private CopiaRepository copiaRepository;

    @Override
    public List<CopiaDTO> obtenerDisponibles() {
        return copiaRepository.obtenerDisponibles();
    }

    @Override
    public CopiaDTO obtenerPorId(Integer idCopia) {
        return copiaRepository.obtenerPorId(idCopia);
    }

    @Override
    public boolean esDisponible(Integer idCopia) {
        return copiaRepository.esDisponible(idCopia);
    }

    @Override
    public void marcarDanada(Integer idCopia) {
        copiaRepository.marcarDanada(idCopia);
    }
}
