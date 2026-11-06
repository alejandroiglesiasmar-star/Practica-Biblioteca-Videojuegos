package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping("/videojuegos")
    public String listarVideojuegos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer idDesarrolladora,
            @RequestParam(required = false) Integer anioDesde,
            @RequestParam(required = false) Integer pegiMax,
            @RequestParam(required = false) Boolean soloDisponibles,
            Model model) {

        List<VideojuegoDTO> lista = videojuegoService.buscarConFiltros(titulo, idDesarrolladora, anioDesde, pegiMax);
        model.addAttribute("videojuegos", lista);

        return "videojuegos/lista";
    }
}