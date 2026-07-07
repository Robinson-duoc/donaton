package com.donaton.donaciones.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Estas rutas "cortas" existían antes pero devolvían la vista sin los datos
    // que la plantilla necesita (provocaban error). Ahora redirigen a la ruta
    // real que sí arma el modelo correctamente.

    @GetMapping("/usuarios")
    public String usuarios() {
        return "redirect:/usuarios/registro";
    }

    @GetMapping("/registro-usuarios")
    public String registro() {
        return "redirect:/usuarios/registro";
    }

    @GetMapping("/inventario")
    public String inventario() {
        return "redirect:/inventario/bodega";
    }

    @GetMapping("/donaciones")
    public String donaciones() {
        return "redirect:/donaciones/registro";
    }

    @GetMapping("/panel-inventario")
    public String panelInventario() {
        return "redirect:/inventario/bodega";
    }

    // Nota: "/panel-donaciones" ahora se maneja en DonacionesController,
    // que sí llena el modelo (listaDonaciones, totalRecaudado, donacion).
}
