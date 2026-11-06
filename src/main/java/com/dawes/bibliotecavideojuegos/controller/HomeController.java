package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import com.dawes.bibliotecavideojuegos.service.SocioService;
import com.dawes.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private SocioService socioService;

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalVideojuegos", videojuegoService.contarTotal());
        model.addAttribute("totalSociosActivos", socioService.contarSociosActivos());
        model.addAttribute("totalPrestamosAbiertos", prestamoService.contarPrestamosAbiertos());
        model.addAttribute("totalPrestamosVencidos", prestamoService.contarPrestamosVencidos());

        return "index";
    }
}