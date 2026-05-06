package com.donaton.service;

import com.donaton.model.Donacion;
import com.donaton.repository.DonacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DonacionServiceTest {

    private DonacionRepository donacionRepository;
    private DonacionService donacionService;

    @BeforeEach
    void setup(){

        donacionRepository = Mockito.mock(DonacionRepository.class);

        donacionService =
                new DonacionService(donacionRepository);
    }

    @Test
    void deberiaGuardarDonacion(){

        Donacion donacion =
                new Donacion(
                        "Juan",
                        "Alimentos",
                        10
                );

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
    void deberiaListarDonaciones(){

        List<Donacion> lista =
                List.of(
                        new Donacion(
                                "Maria",
                                "Ropa",
                                5
                        )
                );

        when(donacionRepository.findAll())
                .thenReturn(lista);

        List<Donacion> resultado =
                donacionService.listarDonaciones();

        assertEquals(1, resultado.size());

        verify(donacionRepository, times(1))
                .findAll();
    }
}