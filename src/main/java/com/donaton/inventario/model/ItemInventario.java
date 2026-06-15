    package com.donaton.inventario.model;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "inventario")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ItemInventario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // Vinculamos el producto al donante mediante su ID plano (Desacoplado)
        @Column(name = "usuario_id", nullable = false)
        private Long usuarioId;

        @Column(nullable = false, length = 100)
        private String nombreProducto;

        @Column(nullable = false, length = 50)
        private String categoria; // ej: "Alimentos", "Ropa", "Medicamentos", "Otros"

        @Column(nullable = false)
        private Integer cantidad;

        @Column(name = "fecha_ingreso")
        private LocalDateTime fechaIngreso;

        @PrePersist
        protected void onCreate() {
            this.fechaIngreso = LocalDateTime.now();
        }
    }