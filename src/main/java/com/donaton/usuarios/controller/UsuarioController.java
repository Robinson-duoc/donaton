package com.donaton.usuarios.controller;

import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. Mostrar la página con el formulario de registro y la lista de usuarios
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        // Pasamos un objeto Usuario vacío al formulario de Thymeleaf
        model.addAttribute("usuario", new Usuario());
        // Pasamos la lista de usuarios existentes para mostrarla en una tabla abajo
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        return "registro-usuarios"; // Esto buscará un archivo llamado 'registro-usuarios.html'
    }

    // 2. Procesar los datos enviados por el formulario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            // Asignamos un rol por defecto para los que se registran en la web
            usuario.setRol("DONANTE");
            usuarioService.registrarUsuario(usuario);
            return "redirect:/usuarios/registro?exito";
        } catch (RuntimeException e) {
            // Si el correo ya existía, capturamos el error y lo mandamos a la vista
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
            return "registro-usuarios";
        }
    }
}