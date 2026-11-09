package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.dto.DesarrolladoraDTO;
import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.exception.ReglaNegocioException;
import com.dawes.bibliotecavideojuegos.service.DesarrolladoraService;
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

    @Autowired
    private DesarrolladoraService desarrolladoraService;

    @GetMapping("/videojuegos")
    public String listarVideojuegos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer idDesarrolladora,
            @RequestParam(required = false) Integer anioDesde,
            @RequestParam(required = false) Integer pegiMaximo,
            @RequestParam(required = false) Boolean soloDisponibles,
            Model model) {

        List<VideojuegoDTO> videojuegos = videojuegoService.buscarConFiltros(titulo, idDesarrolladora, anioDesde, pegiMaximo, soloDisponibles);
        List<DesarrolladoraDTO> desarrolladoras = desarrolladoraService.obtenerTodas();

        model.addAttribute("videojuegos", videojuegos);
        model.addAttribute("desarrolladoras", desarrolladoras);
        model.addAttribute("tituloFiltro", titulo);
        model.addAttribute("desarrolladoraFiltro", idDesarrolladora);
        model.addAttribute("anioDesdeFiltro", anioDesde);
        model.addAttribute("pegiMaximoFiltro", pegiMaximo);
        model.addAttribute("soloDisponiblesFiltro", Boolean.TRUE.equals(soloDisponibles));

        return "videojuegos/lista";
    }

    @GetMapping("/videojuegos/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("desarrolladoras", desarrolladoraService.obtenerTodas());
        return "videojuegos/insertar";
    }

    @PostMapping("/videojuegos/insertar")
    public String insertarVideojuego(
            @RequestParam String titulo,
            @RequestParam int anioLanzamiento,
            @RequestParam int pegi,
            @RequestParam int idDesarrolladora,
            Model model) {

        try {
            videojuegoService.insertarVideojuego(titulo, anioLanzamiento, pegi, idDesarrolladora);
            model.addAttribute("mensajeExito", "Videojuego insertado correctamente.");
        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("desarrolladoras", desarrolladoraService.obtenerTodas());
        return "videojuegos/insertar";
    }
}
