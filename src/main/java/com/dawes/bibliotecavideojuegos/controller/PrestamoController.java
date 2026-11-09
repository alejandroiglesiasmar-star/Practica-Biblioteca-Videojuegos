package com.dawes.bibliotecavideojuegos.controller;

import com.dawes.bibliotecavideojuegos.dto.CopiaDTO;
import com.dawes.bibliotecavideojuegos.dto.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import com.dawes.bibliotecavideojuegos.exception.ReglaNegocioException;
import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/prestamos")
    public String listarPrestamos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            Model model) {

        model.addAttribute("prestamos", prestamoService.buscarConFiltros(estado, fechaDesde));
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("fechaDesdeFiltro", fechaDesde);
        return "prestamos/lista";
    }

    @GetMapping("/prestamos/nuevo")
    public String formularioNuevo(Model model) {
        List<SocioDTO> socios = prestamoService.obtenerSociosActivos();
        List<CopiaDTO> copias = prestamoService.obtenerCopiasDisponibles();
        model.addAttribute("socios", socios);
        model.addAttribute("copias", copias);
        return "prestamos/formulario";
    }

    @PostMapping("/prestamos/registrar")
    public String registrarPrestamo(
            @RequestParam Integer idSocio,
            @RequestParam Integer idCopia,
            Model model) {

        try {
            PrestamoDTO prestamo = prestamoService.registrarPrestamo(idSocio, idCopia);
            model.addAttribute("prestamo", prestamo);
            model.addAttribute("mensajeExito", "Préstamo registrado correctamente.");
            return "prestamos/resumen";
        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("socios", prestamoService.obtenerSociosActivos());
            model.addAttribute("copias", prestamoService.obtenerCopiasDisponibles());
            return "prestamos/formulario";
        }
    }

    @PostMapping("/prestamos/{id}/devolver")
    public String devolverPrestamo(
            @PathVariable Integer id,
            @RequestParam(required = false) boolean copiaDanada,
            Model model) {

        try {
            PrestamoDTO prestamo = prestamoService.devolverPrestamo(id, copiaDanada);
            model.addAttribute("prestamo", prestamo);
            model.addAttribute("mensajeExito", "Devolución registrada correctamente.");
            return "prestamos/resumen";
        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("prestamos", prestamoService.buscarConFiltros(null, null));
            return "prestamos/lista";
        }
    }
}
