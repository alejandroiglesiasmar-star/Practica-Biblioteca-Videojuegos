package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.IDesarrolladoraDAO;
import com.dawes.bibliotecavideojuegos.dto.DesarrolladoraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesarrolladoraService {

    @Autowired
    private IDesarrolladoraDAO desarrolladoraDAO;

    public List<DesarrolladoraDTO> obtenerTodas() {
        return desarrolladoraDAO.obtenerTodas();
    }
}
