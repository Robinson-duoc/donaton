package com.donaton.donaciones.service;

import com.donaton.donaciones.model.Donacion;
import com.donaton.donaciones.repository.DonacionRepository;
import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonacionServiceTest {

    @Mock
    private DonacionRepository donacionRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private DonacionService donacionService;

    private Donacion donacion;
    private Usuario usuario;

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setId(1L);

        donacion = new Donacion();
        donacion.setUsuarioId(1L);
        donacion.setMonto(10000.0);
    }

    // TEST 1 → registrar donación correctamente
    @Test
    void registrarDonacion_DeberiaGuardar() {

        when(usuarioService.obtenerPorId(1L))
                .thenReturn(Optional.of(usuario));

        when(donacionRepository.save(donacion))
                .thenReturn(donacion);

        Donacion resultado =
                donacionService.registrarDonacion(donacion);

        assertNotNull(resultado);

        verify(usuarioService)
                .obtenerPorId(1L);

        verify(donacionRepository)
                .save(donacion);
    }

    // TEST 2 → usuario inexistente
    @Test
    void registrarDonacion_DeberiaLanzarErrorSiUsuarioNoExiste() {

        when(usuarioService.obtenerPorId(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> donacionService.registrarDonacion(donacion)
                );

        assertEquals(
                "Error: El ID de usuario proporcionado no existe.",
                ex.getMessage()
        );

        verify(donacionRepository, never())
                .save(any());
    }

    // TEST 3 → monto inválido
    @Test
    void registrarDonacion_DeberiaLanzarErrorSiMontoEsCero() {

        donacion.setMonto(0.0);

        when(usuarioService.obtenerPorId(1L))
                .thenReturn(Optional.of(usuario));

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> donacionService.registrarDonacion(donacion)
                );

        assertEquals(
                "El monto de la donación debe ser mayor a cero.",
                ex.getMessage()
        );
    }

    // TEST 4 → total recaudado
    @Test
    void obtenerTotalDonado_DeberiaRetornarTotal() {

        when(donacionRepository.obtenerTotalRecaudado())
                .thenReturn(50000.0);

        Double total =
                donacionService.obtenerTotalDonado();

        assertEquals(
                50000.0,
                total
        );

        verify(donacionRepository)
                .obtenerTotalRecaudado();
    }

    // TEST 5 → total nulo retorna 0
    @Test
    void obtenerTotalDonado_DeberiaRetornarCero() {

        when(donacionRepository.obtenerTotalRecaudado())
                .thenReturn(null);

        Double total =
                donacionService.obtenerTotalDonado();

        assertEquals(
                0.0,
                total
        );
    }

    // TEST 6 → obtener todas
    @Test
    void obtenerTodas_DeberiaRetornarLista() {

        List<Donacion> lista =
                Arrays.asList(donacion);

        when(donacionRepository.findAll())
                .thenReturn(lista);

        List<Donacion> resultado =
                donacionService.obtenerTodas();

        assertEquals(
                1,
                resultado.size()
        );

        verify(donacionRepository)
                .findAll();
    }
}