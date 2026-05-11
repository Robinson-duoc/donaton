package com.donaton.service;

import com.donaton.model.Donante;
import com.donaton.repository.DonanteRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonanteService {

    private final DonanteRepository donanteRepository;

    public DonanteService(DonanteRepository donanteRepository) {
        this.donanteRepository = donanteRepository;
    }

    // LISTAR
    public List<Donante> listarDonante() {
        return donanteRepository.findAll();
    }

    // GUARDAR
    public Donante guardarDonante(Donante donante) {
        return donanteRepository.save(donante);
    }

    // ELIMINAR
    public void eliminarDonante(Long id) {
        donanteRepository.deleteById(id);
    }
}