package com.donaton.usuarios.controller;

import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. Mostrar el formulario de login
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "next", required = false) String next,
                                HttpSession session, Model model) {

        // Si ya hay una sesión activa, no tiene sentido mostrar el login de nuevo
        if (session.getAttribute("usuarioId") != null) {
            return "redirect:/";
        }

        model.addAttribute("next", next);
        return "login"; // Busca 'login.html'
    }

    // 2. Procesar el login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam(value = "next", required = false) String next,
                                 HttpSession session, Model model) {

        Optional<Usuario> usuarioOpt = usuarioService.autenticar(email, password);

        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "Correo o contraseña incorrectos, o el usuario no está registrado.");
            model.addAttribute("next", next);
            return "login";
        }

        Usuario usuario = usuarioOpt.get();

        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNombre", usuario.getNombre());
        session.setAttribute("usuarioRol", usuario.getRol());

        // Si el usuario venía de intentar entrar a una página protegida, lo devolvemos ahí
        if (next != null && next.startsWith("/") && !next.startsWith("//")) {
            return "redirect:" + next;
        }

        return "redirect:/";
    }

    // 3. Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruye la sesión: cualquier enlace/página protegida dejará de funcionar
        return "redirect:/login?logout";
    }
}
