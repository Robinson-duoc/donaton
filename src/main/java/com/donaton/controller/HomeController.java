package com.donaton.controller;

import com.donaton.service.DonacionService;
import com.donaton.service.ProductoService;
import com.donaton.service.UsuarioService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final DonacionService donacionService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    public HomeController(
            DonacionService donacionService,
            UsuarioService usuarioService,
            ProductoService productoService
    ) {
        this.donacionService = donacionService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    @GetMapping("/")
    public String inicio(Model model){

        model.addAttribute(
                "totalDonaciones",
                donacionService.listarDonaciones().size()
        );

        model.addAttribute(
                "totalUsuarios",
                usuarioService.listarUsuarios().size()
        );

        model.addAttribute(
                "totalProductos",
                productoService.listarProductos().size()
        );

        model.addAttribute(
                "donaciones",
                donacionService.listarDonaciones()
        );

        return "index";
    }
}