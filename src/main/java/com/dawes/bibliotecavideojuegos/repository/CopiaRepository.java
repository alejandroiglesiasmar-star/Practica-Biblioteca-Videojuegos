package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.dto.CopiaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CopiaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_COPIA_DETALLE =
            "SELECT c.id, c.codigo_inventario AS codigoInventario, c.id_videojuego AS idVideojuego, " +
            "c.id_plataforma AS idPlataforma, c.estado, c.activa, " +
            "v.titulo, p.nombre AS plataforma, v.pegi, v.anio_lanzamiento AS anioLanzamiento " +
            "FROM copias c " +
            "JOIN videojuegos v ON v.id = c.id_videojuego " +
            "JOIN plataformas p ON p.id = c.id_plataforma ";

    public List<CopiaDTO> obtenerDisponibles() {
        String sql = SQL_COPIA_DETALLE +
                "WHERE c.activa = 1 AND NOT EXISTS (" +
                "  SELECT 1 FROM prestamos pr WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL" +
                ") ORDER BY v.titulo, p.nombre";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CopiaDTO.class));
    }

    public CopiaDTO obtenerPorId(Integer idCopia) {
        String sql = SQL_COPIA_DETALLE + "WHERE c.id = ?";
        List<CopiaDTO> resultado = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CopiaDTO.class), idCopia);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public boolean esDisponible(Integer idCopia) {
        String sql = "SELECT COUNT(*) FROM copias c WHERE c.id = ? AND c.activa = 1 AND NOT EXISTS (" +
                "  SELECT 1 FROM prestamos pr WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL" +
                ")";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idCopia);
        return count != null && count > 0;
    }

    public void marcarDanada(Integer idCopia) {
        String sql = "UPDATE copias SET estado = 'DAÑADO', activa = 0 WHERE id = ?";
        jdbcTemplate.update(sql, idCopia);
    }
}
