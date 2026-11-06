package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SocioDAOImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<SocioDTO> socioRowMapper = new RowMapper<SocioDTO>() {
        @Override
        public SocioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SocioDTO socio = new SocioDTO();
            socio.setId(rs.getInt("id"));
            socio.setNombre(rs.getString("nombre"));
            socio.setApellidos(rs.getString("apellidos"));
            socio.setEmail(rs.getString("email"));

            if (rs.getDate("fecha_nacimiento") != null) {
                socio.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            }
            if (rs.getDate("fecha_alta") != null) {
                socio.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());
            }

            socio.setActivo(rs.getBoolean("activo"));
            socio.setEdad(rs.getInt("edad"));
            socio.setPrestamosAbiertos(rs.getInt("prestamosAbiertos"));

            return socio;
        }
    };

    public List<SocioDTO> buscarConFiltros(String nombreApellidos, String email, Integer activo) {
        StringBuilder sql = new StringBuilder(
                "SELECT s.id, s.nombre, s.apellidos, s.email, s.fecha_nacimiento, s.fecha_alta, s.activo, " +
                        "TIMESTAMPDIFF(YEAR, s.fecha_nacimiento, CURDATE()) AS edad, " +
                        "(SELECT COUNT(*) FROM prestamos p WHERE p.id_socio = s.id AND p.fecha_devolucion IS NULL) AS prestamosAbiertos " +
                        "FROM socios s WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (nombreApellidos != null && !nombreApellidos.trim().isEmpty()) {
            sql.append(" AND (s.nombre LIKE ? OR s.apellidos LIKE ?)");
            params.add("%" + nombreApellidos.trim() + "%");
            params.add("%" + nombreApellidos.trim() + "%");
        }

        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND s.email LIKE ?");
            params.add("%" + email.trim() + "%");
        }

        if (activo != null) {
            sql.append(" AND s.activo = ?");
            params.add(activo);
        }

        return jdbcTemplate.query(sql.toString(), socioRowMapper, params.toArray());
    }

    public List<SocioDTO> obtenerTodos() {
        return buscarConFiltros(null, null, null);
    }
    public int contarActivos() {
        String sql = "SELECT COUNT(*) FROM socios WHERE activo = 1";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }
}