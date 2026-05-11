package com.donaton.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "donaciones")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la persona o empresa que dona
    @Column(nullable = false)
    private String donante;

    // Tipo de donación (ropa, comida, dinero, etc)
    @Column(nullable = false)
    private String tipo;

    // Cantidad donada
    @Column(nullable = false)
    private int cantidad;

    // Fecha de la donación
    @Column(nullable = false)
    private LocalDate fecha;

    // Contacto del donante
    private String contacto;

    // Declaración de recursos
    @Column(length = 500)
    private String declaracionRecursos;

    // Rut de quien registró la donación
    private String rutRegistrador;

    // Constructor vacío
    public Donacion() {
    }

    // Constructor completo
    public Donacion(Long id, String donante, String tipo, int cantidad,
                     LocalDate fecha, String contacto,
                     String declaracionRecursos, String rutRegistrador) {
        this.id = id;
        this.donante = donante;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.contacto = contacto;
        this.declaracionRecursos = declaracionRecursos;
        this.rutRegistrador = rutRegistrador;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDonante() {
        return donante;
    }

    public void setDonante(String donante) {
        this.donante = donante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDeclaracionRecursos() {
        return declaracionRecursos;
    }

    public void setDeclaracionRecursos(String declaracionRecursos) {
        this.declaracionRecursos = declaracionRecursos;
    }

    public String getRutRegistrador() {
        return rutRegistrador;
    }

    public void setRutRegistrador(String rutRegistrador) {
        this.rutRegistrador = rutRegistrador;
    }
}