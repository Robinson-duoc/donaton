package com.donaton.dto;

public class DonacionDTO {

    private String donante;
    private String tipo;
    private int cantidad;

    public DonacionDTO() {
    }

    public DonacionDTO(String donante, String tipo, int cantidad) {
        this.donante = donante;
        this.tipo = tipo;
        this.cantidad = cantidad;
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