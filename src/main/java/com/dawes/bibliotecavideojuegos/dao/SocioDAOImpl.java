package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SocioDAOImpl implements SocioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<SocioDTO> mapper = new RowMapper<SocioDTO>() {
        @Override
        public SocioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SocioDTO socio = new SocioDTO();
            socio.setIdSocio(rs.getInt("id_socio"));
            socio.setNombre(rs.getString("nombre"));
            socio.setApellidos(rs.getString("apellidos"));
            socio.setDni(rs.getString("dni"));
            socio.setEmail(rs.getString("email"));
            socio.setTelefono(rs.getString("telefono"));
            if (rs.getDate("fecha_alta") != null) {
                socio.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());
            }
            return socio;
        }
    };

    @Override
    public List<SocioDTO> buscarTodos() {
        String sql = "SELECT * FROM socios ORDER BY apellidos, nombre";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<SocioDTO> buscarPorNombreODni(String textoBusqueda) {
        String sql = "SELECT * FROM socios WHERE LOWER(nombre) LIKE LOWER(?) OR LOWER(apellidos) LIKE LOWER(?) OR LOWER(dni) LIKE LOWER(?) ORDER BY apellidos, nombre";
        String patron = "%" + textoBusqueda + "%";
        return jdbcTemplate.query(sql, mapper, patron, patron, patron);
    }

    @Override
    public SocioDTO buscarPorId(Integer idSocio) {
        String sql = "SELECT * FROM socios WHERE id_socio = ?";
        List<SocioDTO> lista = jdbcTemplate.query(sql, mapper, idSocio);
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Override
    public int insertar(SocioDTO socio) {
        String sql = "INSERT INTO socios (nombre, apellidos, dni, email, telefono, fecha_alta) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";
        return jdbcTemplate.update(sql, socio.getNombre(), socio.getApellidos(), socio.getDni(), socio.getEmail(), socio.getTelefono());
    }

    @Override
    public boolean existeDni(String dni) {
        String sql = "SELECT COUNT(*) FROM socios WHERE LOWER(dni) = LOWER(?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, dni);
        return count != null && count > 0;
    }
}