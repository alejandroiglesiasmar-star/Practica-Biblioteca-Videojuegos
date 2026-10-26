package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VideojuegoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, Boolean soloDisponibles) {
        StringBuilder sql = new StringBuilder("SELECT * FROM v_videojuegos_detalle WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        if (titulo != null && !titulo.trim().isEmpty()) {
            sql.append(" AND titulo LIKE ?");
            parametros.add("%" + titulo.trim() + "%");
        }
        if (idDesarrolladora != null && idDesarrolladora > 0) {
            sql.append(" AND id_desarrolladora = ?");
            parametros.add(idDesarrolladora);
        }
        if (anioDesde != null) {
            sql.append(" AND anio_lanzamiento >= ?");
            parametros.add(anioDesde);
        }
        if (pegiMaximo != null) {
            sql.append(" AND pegi <= ?");
            parametros.add(pegiMaximo);
        }
        if (soloDisponibles != null && soloDisponibles) {
            sql.append(" AND copias_disponibles > 0");
        }

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(VideojuegoDTO.class), parametros.toArray());
    }

    public int insertar(String titulo, int anioLanzamiento, int pegi, int idDesarrolladora) {
        String sql = "INSERT INTO videojuegos (titulo, anio_lanzamiento, pegi, id_desarrolladora) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, titulo, anioLanzamiento, pegi, idDesarrolladora);
    }
}