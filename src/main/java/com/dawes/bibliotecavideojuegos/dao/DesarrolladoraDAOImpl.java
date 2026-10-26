package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.DesarrolladoraDTO;
import com.dawes.bibliotecavideojuegos.repository.DesarrolladoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DesarrolladoraDAOImpl implements IDesarrolladoraDAO {

    @Autowired
    private DesarrolladoraRepository desarrolladoraRepository;

    @Override
    public List<DesarrolladoraDTO> obtenerTodas() {
        return desarrolladoraRepository.obtenerTodas();
    }
}