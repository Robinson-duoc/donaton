package com.donaton.donaciones.controller;

import com.donaton.donaciones.model.Donacion;
import com.donaton.donaciones.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService donacionService;

    // Mostrar el formulario de donaciones y el total acumulado
    @GetMapping("/panel")
    public String mostrarPanelDonaciones(Model model) {
        model.addAttribute("donacion", new Donacion());
        model.addAttribute("totalRecaudado", donacionService.obtenerTotalDonado());
        model.addAttribute("listaDonaciones", donacionService.obtenerTodas());
        return "panel-donaciones"; // Esto buscará 'panel-donaciones.html'
    }

    // Procesar la nueva donación
    @PostMapping("/enviar")
    public String procesarDonacion(@ModelAttribute("donacion") Donacion donacion, Model model) {
        try {
            donacionService.registrarDonacion(donacion);
            return "redirect:/donaciones/panel?exito";
        } catch (RuntimeException e) {
            // Si el usuario no existe o el monto es inválido, atrapamos el error
            model.addAttribute("error", e.getMessage());
            model.addAttribute("totalRecaudado", donacionService.obtenerTotalDonado());
            model.addAttribute("listaDonaciones", donacionService.obtenerTodas());
            return "panel-donaciones";
        }
    }
}