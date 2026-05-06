package com.donaton.service;

import com.donaton.model.Donacion;
import com.donaton.repository.DonacionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonacionService {

    private final DonacionRepository donacionRepository;

    public DonacionService(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    public List<Donacion> listarDonaciones() {
        return donacionRepository.findAll();
    }

    public Donacion guardarDonacion(Donacion donacion) {
        return donacionRepository.save(donacion);
    }

    public void eliminarDonacion(Long id) {
        donacionRepository.deleteById(id);
    }
}