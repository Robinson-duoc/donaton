package com.donaton.donaciones.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/usuarios")
    public String usuarios() {
        return "usuarios";
    }

    @GetMapping("/registro-usuarios")
    public String registro() {
        return "registro-usuarios";
    }

    @GetMapping("/inventario")
    public String inventario() {
        return "inventario";
    }

    @GetMapping("/donaciones")
    public String donaciones() {
        return "donaciones";
    }

    @GetMapping("/panel-donaciones")
    public String panelDonaciones() {
        return "panel-donaciones";
    }

    @GetMapping("/panel-inventario")
    public String panelInventario() {
        return "panel-inventario";
    }

}