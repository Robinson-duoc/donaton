package com.donaton.donaciones.controller;

import com.donaton.donaciones.model.Donacion;
import com.donaton.donaciones.service.DonacionService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donaciones")
public class DonacionesController {

    @Autowired
    private DonacionService donacionService;


    // Mostrar formulario + historial

    @GetMapping("/registro")
    public String mostrarFormulario(Model model){

        model.addAttribute(
            "donacion",
            new Donacion()
        );

        model.addAttribute(
            "listaDonaciones",
            donacionService.obtenerTodas()
        );

        return "donaciones";

    }


    // Guardar

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute("donacion")
            Donacion donacion,
            Model model){

        try{

            donacionService.registrarDonacion(
                donacion
            );

            return
            "redirect:/donaciones/registro?exito";

        }

        catch(RuntimeException e){

            model.addAttribute(
                "error",
                e.getMessage()
            );

            model.addAttribute(
                "listaDonaciones",
                donacionService.obtenerTodas()
            );

            return "donaciones";

        }

    }

}