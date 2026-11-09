package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrestamoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_DETALLE =
            "SELECT p.id, p.id_socio AS idSocio, p.id_copia AS idCopia, " +
            "p.fecha_prestamo AS fechaPrestamo, p.fecha_limite AS fechaLimite, " +
            "p.fecha_devolucion AS fechaDevolucion, p.multa, " +
            "CONCAT(s.nombre, ' ', s.apellidos) AS nombreSocio, " +
            "v.titulo AS tituloJuego, pl.nombre AS plataforma " +
            "FROM prestamos p " +
            "JOIN socios s ON s.id = p.id_socio " +
            "JOIN copias c ON c.id = p.id_copia " +
            "JOIN videojuegos v ON v.id = c.id_videojuego " +
            "JOIN plataformas pl ON pl.id = c.id_plataforma ";

    public List<PrestamoDTO> buscarConFiltros(String estado, LocalDate fechaDesde) {
        StringBuilder sql = new StringBuilder(SQL_DETALLE + "WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (fechaDesde != null) {
            sql.append(" AND p.fecha_prestamo >= ?");
            params.add(Date.valueOf(fechaDesde));
        }

        // El estado se calcula en Java, así que filtramos por las condiciones equivalentes en SQL
        if ("devueltos".equals(estado)) {
            sql.append(" AND p.fecha_devolucion IS NOT NULL");
        } else if ("vencidos".equals(estado)) {
            sql.append(" AND p.fecha_devolucion IS NULL AND p.fecha_limite < CURDATE()");
        } else if ("enplazo".equals(estado)) {
            sql.append(" AND p.fecha_devolucion IS NULL AND p.fecha_limite >= CURDATE()");
        }

        sql.append(" ORDER BY p.fecha_prestamo DESC, p.id DESC");
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(PrestamoDTO.class), params.toArray());
    }

    public PrestamoDTO obtenerPorId(Integer idPrestamo) {
        String sql = SQL_DETALLE + "WHERE p.id = ?";
        List<PrestamoDTO> resultado = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PrestamoDTO.class), idPrestamo);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public Integer insertar(PrestamoDTO prestamo) {
        String sql = "INSERT INTO prestamos (id_socio, id_copia, fecha_prestamo, fecha_limite, fecha_devolucion, multa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, prestamo.getIdSocio());
            ps.setInt(2, prestamo.getIdCopia());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaLimite()));
            ps.setDate(5, prestamo.getFechaDevolucion() != null ? Date.valueOf(prestamo.getFechaDevolucion()) : null);
            ps.setDouble(6, prestamo.getMulta() != null ? prestamo.getMulta() : 0.0);
            return ps;
        }, keyHolder);
        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    public void registrarDevolucion(Integer idPrestamo, LocalDate fechaDevolucion, double multa) {
        String sql = "UPDATE prestamos SET fecha_devolucion = ?, multa = ? WHERE id = ? AND fecha_devolucion IS NULL";
        jdbcTemplate.update(sql, Date.valueOf(fechaDevolucion), multa, idPrestamo);
    }

    public int contarAbiertos() {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE fecha_devolucion IS NULL";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int contarVencidos() {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE fecha_devolucion IS NULL AND fecha_limite < CURDATE()";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int contarAbiertosPorSocio(Integer idSocio) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE id_socio = ? AND fecha_devolucion IS NULL";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idSocio);
        return count != null ? count : 0;
    }

    public boolean tieneVencidosSinDevolver(Integer idSocio) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE id_socio = ? AND fecha_devolucion IS NULL AND fecha_limite < CURDATE()";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idSocio);
        return count != null && count > 0;
    }
}
