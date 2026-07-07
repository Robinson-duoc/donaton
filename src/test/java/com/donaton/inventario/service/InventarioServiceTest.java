package com.donaton.inventario.service;

import com.donaton.inventario.model.ItemInventario;
import com.donaton.inventario.repository.InventarioRepository;
import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private InventarioService inventarioService;

    private ItemInventario item;
    private Usuario usuario;

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setId(1L);

        item = new ItemInventario();
        item.setUsuarioId(1L);
        item.setCantidad(10);
    }

    // TEST 1 → registrar correctamente
    @Test
    void registrarItem_DeberiaGuardarItem() {

        when(usuarioService.obtenerPorId(1L))
                .thenReturn(Optional.of(usuario));

        when(inventarioRepository.save(item))
                .thenReturn(item);

        ItemInventario resultado =
                inventarioService.registrarItem(item);

        assertNotNull(resultado);

        verify(usuarioService)
                .obtenerPorId(1L);

        verify(inventarioRepository)
                .save(item);
    }

    // TEST 2 → usuario inexistente
    @Test
    void registrarItem_DeberiaLanzarErrorSiUsuarioNoExiste() {

        when(usuarioService.obtenerPorId(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> inventarioService.registrarItem(item)
                );

        assertEquals(
                "Error: El ID de usuario proporcionado no existe.",
                ex.getMessage()
        );

        verify(inventarioRepository, never())
                .save(any());
    }

    // TEST 3 → cantidad inválida
    @Test
    void registrarItem_DeberiaLanzarErrorSiCantidadEsCero() {

        item.setCantidad(0);

        when(usuarioService.obtenerPorId(1L))
                .thenReturn(Optional.of(usuario));

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> inventarioService.registrarItem(item)
                );

        assertEquals(
                "La cantidad del producto debe ser mayor a cero.",
                ex.getMessage()
        );
    }

    // TEST 4 → obtener inventario
    @Test
    void obtenerTodo_DeberiaRetornarLista() {

        List<ItemInventario> items =
                Arrays.asList(item);

        when(inventarioRepository.findAll())
                .thenReturn(items);

        List<ItemInventario> resultado =
                inventarioService.obtenerTodo();

        assertEquals(1, resultado.size());

        verify(inventarioRepository)
                .findAll();
    }
}