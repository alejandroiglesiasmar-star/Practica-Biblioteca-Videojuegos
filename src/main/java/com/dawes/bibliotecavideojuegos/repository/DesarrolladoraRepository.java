package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.dto.DesarrolladoraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DesarrolladoraRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DesarrolladoraDTO> obtenerTodas() {
        String sql = "SELECT id, nombre, pais FROM desarrolladoras";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DesarrolladoraDTO.class));
    }
}