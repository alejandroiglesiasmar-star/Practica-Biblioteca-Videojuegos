package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.dto.DesarrolladoraDTO;
import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    // Página de bienvenida / Inicio
    @GetMapping("/")
    public String paginaInicio() {
        return "index"; // Carga index.html de la carpeta templates
    }

    // RF-01: Listar videojuegos con filtros opcionales y combinables
    @GetMapping("/videojuegos")
    public String listarVideojuegos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer idDesarrolladora,
            @RequestParam(required = false) Integer anioDesde,
            @RequestParam(required = false) Integer pegiMaximo,
            @RequestParam(required = false) Boolean soloDisponibles,
            Model model) {

        List<VideojuegoDTO> lista = videojuegoService.buscarConFiltros(titulo, idDesarrolladora, anioDesde, pegiMaximo, soloDisponibles);
        List<DesarrolladoraDTO> desarrolladoras = videojuegoService.obtenerDesarrolladoras();

        model.addAttribute("videojuegos", lista);
        model.addAttribute("desarrolladoras", desarrolladoras);

        // Mantener los valores en los filtros de la vista
        model.addAttribute("tituloFiltro", titulo);
        model.addAttribute("desarrolladoraFiltro", idDesarrolladora);
        model.addAttribute("anioDesdeFiltro", anioDesde);
        model.addAttribute("pegiMaximoFiltro", pegiMaximo);
        model.addAttribute("soloDisponiblesFiltro", soloDisponibles);

        return "videojuegos/listar"; // Ruta de la vista HTML
    }

    // RF-02: Mostrar formulario de inserción
    @GetMapping("/videojuegos/nuevo")
    public String mostrarFormularioInsercion(Model model) {
        model.addAttribute("desarrolladoras", videojuegoService.obtenerDesarrolladoras());
        return "videojuegos/insertar"; // Ruta de la vista HTML
    }

    // RF-02: Procesar la inserción del videojuego
    @PostMapping("/videojuegos/insertar")
    public String insertarVideojuego(
            @RequestParam String titulo,
            @RequestParam Integer anioLanzamiento,
            @RequestParam Integer pegi,
            @RequestParam Integer idDesarrolladora,
            Model model) {

        String resultado = videojuegoService.insertarVideojuego(titulo, anioLanzamiento, pegi, idDesarrolladora);

        if ("EXITO".equals(resultado)) {
            model.addAttribute("mensajeExito", "Videojuego \"" + titulo + "\" insertado correctamente.");
        } else {
            model.addAttribute("error", resultado);
        }

        model.addAttribute("desarrolladoras", videojuegoService.obtenerDesarrolladoras());
        return "videojuegos/insertar";
    }
}