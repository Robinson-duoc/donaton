package com.donaton.inventario.controller;

import com.donaton.inventario.model.ItemInventario;
import com.donaton.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // Mostrar el formulario de ingreso y la lista de la bodega
    @GetMapping("/bodega")
    public String mostrarBodega(Model model) {
        model.addAttribute("item", new ItemInventario());
        model.addAttribute("listaProductos", inventarioService.obtenerTodo());
        return "panel-inventario"; // Buscará 'panel-inventario.html'
    }

    // Registrar el producto físico
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("item") ItemInventario item, Model model) {
        try {
            inventarioService.registrarItem(item);
            return "redirect:/inventario/bodega?exito";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaProductos", inventarioService.obtenerTodo());
            return "panel-inventario";
        }
    }
}