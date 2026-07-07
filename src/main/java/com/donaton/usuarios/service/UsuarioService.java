package com.donaton.usuarios.service;

import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Registrar un nuevo usuario con una validación básica
    public Usuario registrarUsuario(Usuario usuario) {
        // Regla de negocio: No permitir correos duplicados
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El correo ya está registrado en la Donatón.");
        }
        
        // Por ahora guardamos la contraseña en texto plano. 
        // ¡Más adelante le meteremos seguridad con Spring Security BCrypt!
        return usuarioRepository.save(usuario);
    }

    // 2. Obtener todos los usuarios (útil para reportes del administrador)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // 3. Buscar un usuario por su ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // 4. Autenticar (login): valida que el correo exista y que la contraseña coincida
    public Optional<Usuario> autenticar(String email, String password) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);

        if (usuarioExistente.isPresent() && usuarioExistente.get().getPassword().equals(password)) {
            return usuarioExistente;
        }

        // Si el correo no existe o la contraseña no coincide, no hay sesión válida
        return Optional.empty();
    }
}