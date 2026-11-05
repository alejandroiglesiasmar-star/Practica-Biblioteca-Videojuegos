package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import com.dawes.bibliotecavideojuegos.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SocioController {

    @Autowired
    private SocioService socioService;

    @GetMapping("/socios")
    public String listarSocios(@RequestParam(value = "filtro", required = false) String filtro, Model model) {
        List<SocioDTO> lista = socioService.buscarSocios(filtro);
        model.addAttribute("socios", lista);
        model.addAttribute("filtro", filtro);
        return "socios/lista";
    }

    @GetMapping("/socios/nuevo")
    public String mostrarFormularioInsertar(Model model) {
        model.addAttribute("socio", new SocioDTO());
        return "socios/insertar";
    }

    @PostMapping("/socios/guardar")
    public String guardarSocio(@ModelAttribute SocioDTO socio, Model model) {
        StringBuilder mensajeError = new StringBuilder();
        boolean exito = socioService.guardarSocio(socio, mensajeError);

        if (!exito) {
            model.addAttribute("error", mensajeError.toString());
            model.addAttribute("socio", socio);
            return "socios/insertar";
        }

        return "redirect:/socios";
    }
}