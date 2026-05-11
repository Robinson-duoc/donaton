package com.donaton.controller;

import com.donaton.dto.DonanteDTO;
import com.donaton.model.Donante;
import com.donaton.service.DonanteService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donantes")
public class DonanteController {

    private final DonanteService donanteService;

    public DonanteController(DonanteService donanteService) {
        this.donanteService = donanteService;
    }

    @GetMapping
    public List<Donante> listarDonante() {
        return donanteService.listarDonante();
    }

    @PostMapping
    public Donante guardarDonante(@RequestBody DonanteDTO dto) {

        Donante donante = new Donante();
        donante.setNombre(dto.getNombre());
        donante.setCorreo(dto.getCorreo());

        return donanteService.guardarDonante(donante);
    }

    @DeleteMapping("/{id}")
    public void eliminarDonante(@PathVariable Long id) {
        donanteService.eliminarDonante(id);
    }
}