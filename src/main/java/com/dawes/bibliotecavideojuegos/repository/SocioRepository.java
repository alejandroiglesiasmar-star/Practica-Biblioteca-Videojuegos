package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SocioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SOCIO_DETALLE =
            "SELECT s.id, s.nombre, s.apellidos, s.email, s.fecha_nacimiento AS fechaNacimiento, " +
            "s.fecha_alta AS fechaAlta, s.activo, " +
            "TIMESTAMPDIFF(YEAR, s.fecha_nacimiento, CURDATE()) AS edad, " +
            "(SELECT COUNT(*) FROM prestamos p WHERE p.id_socio = s.id AND p.fecha_devolucion IS NULL) AS prestamosAbiertos " +
            "FROM socios s ";

    public List<SocioDTO> buscarConFiltros(String nombreApellidos, String email, Integer activo) {
        StringBuilder sql = new StringBuilder(SQL_SOCIO_DETALLE + "WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nombreApellidos != null && !nombreApellidos.trim().isEmpty()) {
            sql.append(" AND (s.nombre LIKE ? OR s.apellidos LIKE ?)");
            String like = "%" + nombreApellidos.trim() + "%";
            params.add(like);
            params.add(like);
        }
        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND s.email LIKE ?");
            params.add("%" + email.trim() + "%");
        }
        if (activo != null) {
            sql.append(" AND s.activo = ?");
            params.add(activo);
        }

        sql.append(" ORDER BY s.apellidos, s.nombre");
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(SocioDTO.class), params.toArray());
    }

    public SocioDTO obtenerPorId(Integer idSocio) {
        String sql = SQL_SOCIO_DETALLE + "WHERE s.id = ?";
        List<SocioDTO> resultado = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SocioDTO.class), idSocio);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<SocioDTO> obtenerActivos() {
        String sql = "SELECT id, nombre, apellidos, email FROM socios WHERE activo = 1 ORDER BY apellidos, nombre";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SocioDTO.class));
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM socios WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public void insertar(SocioDTO socio) {
        String sql = "INSERT INTO socios (nombre, apellidos, email, fecha_nacimiento, fecha_alta, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                socio.getNombre(),
                socio.getApellidos(),
                socio.getEmail(),
                socio.getFechaNacimiento() != null ? Date.valueOf(socio.getFechaNacimiento()) : null,
                Date.valueOf(socio.getFechaAlta()),
                socio.getActivo() != null ? socio.getActivo() : true);
    }

    public int contarActivos() {
        String sql = "SELECT COUNT(*) FROM socios WHERE activo = 1";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }
}
