package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import com.dawes.bibliotecavideojuegos.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SocioController {

    @Autowired
    private SocioService socioService;

    @GetMapping("/socios")
    public String listarSocios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer activo,
            Model model) {

        model.addAttribute("socios", socioService.buscarConFiltros(nombre, email, activo));
        return "socios/lista";
    }
}