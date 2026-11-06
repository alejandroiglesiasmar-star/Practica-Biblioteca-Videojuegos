package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PrestamoDAOImpl implements PrestamoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<PrestamoDTO> napper = new RowMapper<PrestamoDTO>() {
        @Override
        public PrestamoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PrestamoDTO prestamo = new PrestamoDTO();
            prestamo.setId(rs.getInt("id"));
            prestamo.setIdSocio(rs.getInt("id_socio"));
            prestamo.setIdCopia(rs.getInt("id_copia"));

            if (rs.getDate("fecha_prestamo") != null) {
                prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
            }
            if (rs.getDate("fecha_limite") != null) {
                prestamo.setFechaLimite(rs.getDate("fecha_limite").toLocalDate());
            }
            if (rs.getDate("fecha_devolucion") != null) {
                prestamo.setFechaDevolucion(rs.getDate("fecha_devolucion").toLocalDate());
            }
            if (rs.getObject("multa") != null) {
                prestamo.setMulta(rs.getDouble("multa"));
            }
            return prestamo;
        }
    };

    @Override
    public List<PrestamoDTO> buscarTodos() {
        String sql = "SELECT id, id_socio, id_copia, fecha_prestamo, fecha_limite, fecha_devolucion, multa FROM prestamos";
        return jdbcTemplate.query(sql, napper);
    }

    @Override
    public void guardar(PrestamoDTO prestamo) {
        String sql = "INSERT INTO prestamos (id_socio, id_copia, fecha_prestamo, fecha_limite, fecha_devolucion, multa) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                prestamo.getIdSocio(),
                prestamo.getIdCopia(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaLimite(),
                prestamo.getFechaDevolucion(),
                prestamo.getMulta() != null ? prestamo.getMulta() : 0.0
        );
    }
}