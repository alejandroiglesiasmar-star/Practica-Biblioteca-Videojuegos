package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VideojuegoDAOImpl implements IVideojuegoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMax) {
        StringBuilder sql = new StringBuilder("SELECT id, titulo, anio_lanzamiento AS anioLanzamiento, pegi, id_desarrolladora AS idDesarrolladora FROM videojuegos WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (titulo != null && !titulo.trim().isEmpty()) {
            sql.append(" AND titulo LIKE ?");
            params.add("%" + titulo.trim() + "%");
        }
        if (idDesarrolladora != null) {
            sql.append(" AND id_desarrolladora = ?");
            params.add(idDesarrolladora);
        }
        if (anioDesde != null) {
            sql.append(" AND anio_lanzamiento >= ?");
            params.add(anioDesde);
        }
        if (pegiMax != null) {
            sql.append(" AND pegi <= ?");
            params.add(pegiMax);
        }

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(VideojuegoDTO.class), params.toArray());
    }

    @Override
    public int insertar(String titulo, int anioLanzamiento, int pegi, int idDesarrolladora) {
        String sql = "INSERT INTO videojuegos (titulo, anio_lanzamiento, pegi, id_desarrolladora) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, titulo, anioLanzamiento, pegi, idDesarrolladora);
        // Opcional: recuperar el ID generado si lo necesitas
        return 1;
    }

    @Override
    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM videojuegos";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }
}