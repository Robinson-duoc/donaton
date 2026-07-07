package com.donaton.usuario.service; // CORREGIDO: Añadido '.donaton'

import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.repository.UsuarioRepository;
import com.donaton.usuarios.service.UsuarioService; // CORREGIDO: Importación explícita del servicio
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioPrueba;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks de Mockito antes de cada prueba
        MockitoAnnotations.openMocks(this);
        
        // Creamos un usuario base para usar en los tests
        usuarioPrueba = new Usuario();
        usuarioPrueba.setId(1L);
        usuarioPrueba.setNombre("Juan Perez");
        usuarioPrueba.setEmail("juan@donaton.cl");
        usuarioPrueba.setPassword("123456");
    }

    @Test
    void testRegistrarUsuario_Exitoso() {
        // Simulamos que el correo NO existe en la base de datos
        when(usuarioRepository.findByEmail(usuarioPrueba.getEmail())).thenReturn(Optional.empty());
        // Simulamos que al guardar, la base de datos retorna el usuario guardado con éxito
        when(usuarioRepository.save(usuarioPrueba)).thenReturn(usuarioPrueba);

        // Ejecutamos el método del servicio
        Usuario resultado = usuarioService.registrarUsuario(usuarioPrueba);

        // Verificaciones (Asserts) para el informe
        assertNotNull(resultado);
        assertEquals("juan@donaton.cl", resultado.getEmail());
        
        // Verificamos que el repositorio realmente fue llamado
        verify(usuarioRepository, times(1)).findByEmail(usuarioPrueba.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioPrueba);
    }

    @Test
    void testRegistrarUsuario_Error_CorreoDuplicado() {
        // Simulamos que el correo SÍ existe previamente en la base de datos
        when(usuarioRepository.findByEmail(usuarioPrueba.getEmail())).thenReturn(Optional.of(usuarioPrueba));

        // Verificamos que lance la excepción esperada y capturemos el mensaje de error
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuario(usuarioPrueba);
        });

        assertEquals("El correo ya está registrado en la Donatón.", excepcion.getMessage());
        
        // Verificamos que NUNCA se llamó al método save porque saltó el error antes
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}