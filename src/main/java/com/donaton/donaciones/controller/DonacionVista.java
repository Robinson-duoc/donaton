package com.donaton.donaciones.controller;

import com.donaton.donaciones.model.Donacion;

import java.time.format.DateTimeFormatter;

/**
 * DTO usado solo para pintar la tabla de historial en la vista 'donaciones.html',
 * mostrando el nombre del donante en lugar de únicamente su ID.
 */
public record DonacionVista(Long id, String donante, Double monto, String estado, String fecha) {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static DonacionVista desde(Donacion donacion, String nombreDonante) {
        String fechaTexto = donacion.getFechaDonacion() != null
                ? donacion.getFechaDonacion().format(FORMATO)
                : "";

        return new DonacionVista(
                donacion.getId(),
                nombreDonante,
                donacion.getMonto(),
                donacion.getEstado(),
                fechaTexto
        );
    }
}
