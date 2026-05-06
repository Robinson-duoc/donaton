package com.donaton.model;

import jakarta.persistence.*;

@Entity
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String donante;
    private String tipo;
    private int cantidad;

    public Donacion() {
    }

    public Donacion(String donante, String tipo, int cantidad) {
        this.donante = donante;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
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
}