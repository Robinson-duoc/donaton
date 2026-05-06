package com.donaton.controller;

import com.donaton.model.Donacion;
import com.donaton.service.DonacionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DonacionWebController {

    private final DonacionService donacionService;

    public DonacionWebController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    // MOSTRAR TABLA
    @GetMapping("/donaciones-web")
    public String listar(Model model){

        model.addAttribute(
            "donaciones",
            donacionService.listarDonaciones()
        );

        return "donaciones";
    }

    // GUARDAR
    @PostMapping("/guardar-donacion")
    public String guardar(
            @RequestParam String donante,
            @RequestParam String tipo,
            @RequestParam int cantidad
    ){

        Donacion donacion = new Donacion();

        donacion.setDonante(donante);
        donacion.setTipo(tipo);
        donacion.setCantidad(cantidad);

        donacionService.guardarDonacion(donacion);

        return "redirect:/donaciones-web";
    }

    // ELIMINAR
    @GetMapping("/eliminar-donacion/{id}")
    public String eliminar(@PathVariable Long id){

        donacionService.eliminarDonacion(id);

        return "redirect:/donaciones-web";
    }
}