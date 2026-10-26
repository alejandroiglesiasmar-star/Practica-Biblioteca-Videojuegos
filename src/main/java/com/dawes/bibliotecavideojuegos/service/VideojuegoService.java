package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.dao.IDesarrolladoraDAO;
import com.dawes.bibliotecavideojuegos.dao.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.dto.DesarrolladoraDTO;
import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VideojuegoService {

    @Autowired
    private IVideojuegoDAO videojuegoDAO;

    @Autowired
    private IDesarrolladoraDAO desarrolladoraDAO;

    // Listar con filtros (RF-01)[cite: 7]
    public List<VideojuegoDTO> buscarConFiltros(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, Boolean soloDisponibles) {
        return videojuegoDAO.buscarConFiltros(titulo, idDesarrolladora, anioDesde, pegiMaximo, soloDisponibles);
    }

    // Obtener todas las desarrolladoras para los desplegables[cite: 6, 12]
    public List<DesarrolladoraDTO> obtenerDesarrolladoras() {
        return desarrolladoraDAO.obtenerTodas();
    }

    // Insertar videojuego aplicando las validaciones de negocio (RF-02 y 3.3)[cite: 6, 7]
    public String insertarVideojuego(String titulo, Integer anioLanzamiento, Integer pegi, Integer idDesarrolladora) {
        // 1. Validar título obligatorio
        if (titulo == null || titulo.trim().isEmpty()) {
            return "El título del videojuego es obligatorio.";
        }

        // 2. Validar año de lanzamiento (entre 1970 y el año actual)[cite: 6]
        int anioActual = LocalDate.now().getYear();
        if (anioLanzamiento == null || anioLanzamiento < 1970 || anioLanzamiento > anioActual) {
            return "El año de lanzamiento debe estar comprendido entre 1970 y " + anioActual + ".";
        }

        // 3. Validar PEGI (valores válidos: 3, 7, 12, 16, 18)[cite: 6]
        if (pegi == null || (pegi != 3 && pegi != 7 && pegi != 12 && pegi != 16 && pegi != 18)) {
            return "El PEGI seleccionado no es válido.";
        }

        // 4. Validar desarrolladora
        if (idDesarrolladora == null || idDesarrolladora <= 0) {
            return "Debe seleccionar una desarrolladora válida.";
        }

        // Si pasa todas las validaciones, insertamos en la BD
        int resultado = videojuegoDAO.insertar(titulo.trim(), anioLanzamiento, pegi, idDesarrolladora);

        if (resultado > 0) {
            return "EXITO"; // Indicador de éxito para el controlador
        } else {
            return "Error al insertar el videojuego en la base de datos.";
        }
    }
}