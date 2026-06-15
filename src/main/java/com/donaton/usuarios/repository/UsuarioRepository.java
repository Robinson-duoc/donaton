package com.donaton.usuarios.repository;

import com.donaton.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Este método personalizado nos servirá más adelante para el Login 
    // y para evitar registrar correos duplicados.
    Optional<Usuario> findByEmail(String email);
}