package com.donaton.controller;

import com.donaton.dto.DonacionDTO;
import com.donaton.model.Donacion;
import com.donaton.service.DonacionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donaciones")
public class DonacionController {

    private final DonacionService donacionService;

    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    @GetMapping
    public List<Donacion> listarDonaciones() {
        return donacionService.listarDonaciones();
    }

    @PostMapping
    public Donacion guardarDonacion(@RequestBody DonacionDTO dto) {

        Donacion donacion = new Donacion();
        donacion.setDonante(dto.getDonante());
        donacion.setTipo(dto.getTipo());
        donacion.setCantidad(dto.getCantidad());

        return donacionService.guardarDonacion(donacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarDonacion(@PathVariable Long id) {
        donacionService.eliminarDonacion(id);
    }
}