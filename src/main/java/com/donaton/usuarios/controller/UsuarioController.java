package com.donaton.usuarios.controller;

import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite que tu futuro Frontend (NPM) se conecte sin bloquearse por CORS
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. POST: Registrar un usuario (Recibe JSON y devuelve JSON)
    @PostMapping
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {
        try {
            usuario.setRol("DONANTE");
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si el correo está repetido, devolvemos un estado 400 (Bad Request) con el error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2. GET: Obtener todos los usuarios (Devuelve una lista JSON)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // 3. GET: Buscar un usuario por ID (Útil para las validaciones cruzadas)
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}