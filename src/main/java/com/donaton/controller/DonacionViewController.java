package com.donaton.controller;


import com.donaton.model.Donacion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.donaton.service.DonacionService;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@Controller  // ← MVC, no REST
public class DonacionViewController {

    private final DonacionService donacionService;

    public DonacionViewController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    // ── Dashboard ──────────────────────────────────────
    @GetMapping("/")
    public String dashboard(Model model) {
        List<Donacion> donaciones = donacionService.listarDonaciones();
        model.addAttribute("donaciones", donaciones);
        model.addAttribute("totalDonaciones", donaciones.size());
        model.addAttribute("totalUsuarios", 0);   // reemplaza con tu servicio
        model.addAttribute("totalProductos", 0);  // reemplaza con tu servicio
        return "index";
    }

    // ── Vista de donaciones ────────────────────────────
    @GetMapping("/donaciones-web")
    public String donacionesWeb(Model model) {
        model.addAttribute("donaciones", donacionService.listarDonaciones());
        return "donaciones";
    }

    // ── Guardar persona ────────────────────────────────
    @PostMapping("/guardar-donacion-persona")
    public String guardarPersona(
            @RequestParam String nombreApellido,
            @RequestParam String contacto,
            @RequestParam String tipo,
            @RequestParam int cantidad,
            @RequestParam String declaracionRecursos,
            @RequestParam String rutRegistrador) {

        Donacion d = new Donacion();
        d.setDonante(nombreApellido);
        d.setTipo(tipo);
        d.setCantidad(cantidad);
        d.setFecha(LocalDate.now());
        d.setContacto(contacto);
        d.setDeclaracionRecursos(declaracionRecursos);
        d.setRutRegistrador(rutRegistrador);
        donacionService.guardarDonacion(d);

        return "redirect:/donaciones-web";
    }

    // ── Guardar empresa ────────────────────────────────
    @PostMapping("/guardar-donacion-empresa")
    public String guardarEmpresa(
            @RequestParam String nombreEmpresa,
            @RequestParam String tipo,
            @RequestParam int cantidad,
            @RequestParam String declaracionRecursos) {

        Donacion d = new Donacion();
        d.setDonante(nombreEmpresa);
        d.setTipo(tipo);
        d.setCantidad(cantidad);
        d.setFecha(LocalDate.now());
        donacionService.guardarDonacion(d);

        return "redirect:/donaciones-web";
    }

    // ── Eliminar ───────────────────────────────────────
    @GetMapping("/eliminar-donacion/{id}")
    public String eliminar(@PathVariable Long id) {
        donacionService.eliminarDonacion(id);
        return "redirect:/donaciones-web";
    }
}