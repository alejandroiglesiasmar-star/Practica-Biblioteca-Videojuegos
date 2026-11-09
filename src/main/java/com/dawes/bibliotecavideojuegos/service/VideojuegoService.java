package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.exception.ReglaNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

@Service
public class VideojuegoService {

    private static final List<Integer> PEGI_VALIDOS = Arrays.asList(3, 7, 12, 16, 18);

    @Autowired
    private IVideojuegoDAO videojuegoDAO;

    public List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMax, Boolean soloDisponibles) {
        return videojuegoDAO.buscarConFiltros(titulo, idDesarrolladora, anioDesde, pegiMax, soloDisponibles);
    }

    public void insertarVideojuego(String titulo, int anioLanzamiento, int pegi, int idDesarrolladora) {
        validar(titulo, anioLanzamiento, pegi);
        videojuegoDAO.insertar(titulo.trim(), anioLanzamiento, pegi, idDesarrolladora);
    }

    private void validar(String titulo, int anioLanzamiento, int pegi) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ReglaNegocioException("El titulo es obligatorio.");
        }
        int anioActual = Year.now().getValue();
        if (anioLanzamiento < 1970 || anioLanzamiento > anioActual) {
            throw new ReglaNegocioException("El anio de lanzamiento debe estar entre 1970 y " + anioActual + ".");
        }
        if (!PEGI_VALIDOS.contains(pegi)) {
            throw new ReglaNegocioException("El PEGI debe ser uno de: 3, 7, 12, 16 o 18.");
        }
    }

    public int contarTotal() {
        return videojuegoDAO.contarTotal();
    }
}