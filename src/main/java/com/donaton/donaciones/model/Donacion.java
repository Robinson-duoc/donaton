package com.donaton.donaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "donaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Guardamos solo el ID del usuario para mantener los módulos desacoplados
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, length = 20)
    private String estado; // ej: "PENDIENTE", "COMPLETADA", "RECHAZADA"

    @Column(name = "fecha_donacion")
    private LocalDateTime fechaDonacion;

    // Este método se ejecutará automáticamente antes de guardar en la BD
    @PrePersist
    protected void onCreate() {
        this.fechaDonacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "COMPLETADA"; // Por ahora las daremos por completadas directo
        }
    }
}