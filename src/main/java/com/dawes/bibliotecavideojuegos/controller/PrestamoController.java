package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import com.dawes.bibliotecavideojuegos.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private SocioService socioService;

    @GetMapping
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.buscarTodos());
        return "prestamos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("prestamo", new PrestamoDTO());
        model.addAttribute("socios", socioService.obtenerTodosLosSocios());
        return "prestamos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPrestamo(@ModelAttribute PrestamoDTO prestamo) {
        prestamoService.guardarPrestamo(prestamo);
        return "redirect:/prestamos";
    }
}