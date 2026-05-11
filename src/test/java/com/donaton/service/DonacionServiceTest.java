package com.donaton.service;

import com.donaton.model.Donacion;
import com.donaton.repository.DonacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DonacionServiceTest {

    private DonacionRepository donacionRepository;
    private DonacionService donacionService;

    @BeforeEach
    void setup() {

        donacionRepository = Mockito.mock(DonacionRepository.class);

        donacionService =
                new DonacionService(donacionRepository);
    }

    @Test
    void deberiaGuardarDonacion() {

        Donacion donacion = new Donacion();

        donacion.setDonante("Juan");
        donacion.setTipo("Alimentos");
        donacion.setCantidad(10);
        donacion.setFecha(LocalDate.now());

        when(donacionRepository.save(donacion))
                .thenReturn(donacion);

        Donacion resultado =
                donacionService.guardarDonacion(donacion);

        assertEquals(
                "Juan",
                resultado.getDonante()
        );

        verify(donacionRepository, times(1))
                .save(donacion);
    }

    @Test
    void deberiaListarDonaciones() {

        Donacion donacion = new Donacion();

        donacion.setDonante("Maria");
        donacion.setTipo("Ropa");
        donacion.setCantidad(5);
        donacion.setFecha(LocalDate.now());

        List<Donacion> lista = List.of(donacion);

        when(donacionRepository.findAll())
                .thenReturn(lista);

        List<Donacion> resultado =
                donacionService.listarDonaciones();

        assertEquals(1, resultado.size());

        verify(donacionRepository, times(1))
                .findAll();
    }
}