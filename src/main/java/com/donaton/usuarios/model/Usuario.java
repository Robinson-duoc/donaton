package com.donaton.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data // Genera getters, setters, toString, equals y hashCode automáticamente gracias a Lombok
@NoArgsConstructor // Genera el constructor vacío obligatorio para JPA
@AllArgsConstructor // Genera un constructor con todos los campos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    // Aquí puedes definir el rol (ej: "DONANTE", "ADMINISTRADOR") para saber qué puede hacer cada uno
    @Column(nullable = false)
    private String rol;
}