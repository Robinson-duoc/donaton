package com.donaton.donaciones.controller;

import com.donaton.donaciones.model.Donacion;
import com.donaton.donaciones.service.DonacionService;
import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DonacionesController {

    @Autowired
    private DonacionService donacionService;

    @Autowired
    private UsuarioService usuarioService;

    // ===================== Formulario de donación (requiere sesión iniciada) =====================

    // Mostrar formulario + historial
    @GetMapping("/donaciones/registro")
    public String mostrarFormulario(HttpSession session, Model model) {

        model.addAttribute("donacion", new Donacion());
        model.addAttribute("donaciones", construirHistorial());
        model.addAttribute("nombreUsuario", session.getAttribute("usuarioNombre"));

        return "donaciones";
    }

    // Guardar: el usuario que dona SIEMPRE se toma de la sesión, nunca de un campo del formulario.
    // Así garantizamos que solo un usuario registrado y logueado puede donar.
    @PostMapping("/donaciones/guardar")
    public String guardar(@ModelAttribute("donacion") Donacion donacion, HttpSession session, Model model) {

        try {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            donacion.setUsuarioId(usuarioId);

            donacionService.registrarDonacion(donacion);

            return "redirect:/donaciones/registro?exito";

        } catch (RuntimeException e) {

            model.addAttribute("error", e.getMessage());
            model.addAttribute("donaciones", construirHistorial());
            model.addAttribute("nombreUsuario", session.getAttribute("usuarioNombre"));

            return "donaciones";
        }
    }

    // ===================== Panel / dashboard de donaciones (requiere sesión iniciada) =====================

    @GetMapping("/panel-donaciones")
    public String panel(Model model) {

        model.addAttribute("donacion", new Donacion());
        model.addAttribute("listaDonaciones", donacionService.obtenerTodas());
        model.addAttribute("totalRecaudado", donacionService.obtenerTotalDonado());

        return "panel-donaciones";
    }

    @PostMapping("/donaciones/enviar")
    public String enviarDesdePanel(@ModelAttribute("donacion") Donacion donacion, HttpSession session, Model model) {

        try {
            // También en el panel se respeta la sesión activa como dueño de la donación
            donacion.setUsuarioId((Long) session.getAttribute("usuarioId"));
            donacionService.registrarDonacion(donacion);

            return "redirect:/panel-donaciones?exito";

        } catch (RuntimeException e) {

            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaDonaciones", donacionService.obtenerTodas());
            model.addAttribute("totalRecaudado", donacionService.obtenerTotalDonado());

            return "panel-donaciones";
        }
    }

    // ===================== Utilidades internas =====================

    private List<DonacionVista> construirHistorial() {
        return donacionService.obtenerTodas().stream()
                .map(d -> {
                    String nombreDonante = usuarioService.obtenerPorId(d.getUsuarioId())
                            .map(Usuario::getNombre)
                            .orElse("Usuario #" + d.getUsuarioId());
                    return DonacionVista.desde(d, nombreDonante);
                })
                .collect(Collectors.toList());
    }
}
