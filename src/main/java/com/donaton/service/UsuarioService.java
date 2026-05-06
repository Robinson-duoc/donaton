package com.donaton.service;

import com.donaton.model.Usuario;
import com.donaton.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // LISTAR
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // GUARDAR
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ELIMINAR
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}