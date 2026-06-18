package com.donaton.usuarios.service;

import com.donaton.usuarios.model.Usuario;
import com.donaton.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository; // Simulamos la base de datos

    @InjectMocks
    private UsuarioService usuarioService; // Mockito inyecta el repositorio aquí (incluso con @Autowired en propiedad)

    private Usuario usuarioBase;

    @BeforeEach
    void setUp() {
        // Preparamos un usuario de prueba antes de cada test
        usuarioBase = new Usuario(1L, "Carlos Gomez", "carlos@donaton.com", "clave123", "DONANTE");
    }

    // ==========================================
    // PRUEBAS PARA: registrarUsuario()
    // ==========================================

    @Test
    void registrarUsuario_CuandoElCorreoNoExiste_DeberiaGuardarExitosamente() {
        // GIVEN: El email no está registrado y el repositorio guardará y retornará el usuario
        when(usuarioRepository.findByEmail(usuarioBase.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioBase);

        // WHEN: Ejecutamos el método del servicio
        Usuario usuarioGuardado = usuarioService.registrarUsuario(usuarioBase);

        // THEN: Validamos los resultados
        assertNotNull(usuarioGuardado);
        assertEquals("Carlos Gomez", usuarioGuardado.getNombre());
        assertEquals("carlos@donaton.com", usuarioGuardado.getEmail());

        // Verificaciones de comportamiento
        verify(usuarioRepository, times(1)).findByEmail(usuarioBase.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioBase);
    }

    @Test
    void registrarUsuario_CuandoElCorreoYaExiste_DeberiaLanzarRuntimeException() {
        // GIVEN: El email ya se encuentra en la base de datos
        when(usuarioRepository.findByEmail(usuarioBase.getEmail())).thenReturn(Optional.of(usuarioBase));

        // WHEN & THEN: Al ejecutar, se debe lanzar la excepción con el mensaje exacto
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuario(usuarioBase);
        });

        assertEquals("El correo ya está registrado en la Donatón.", excepcion.getMessage());

        // Verificación: Se buscó por email, pero JAMÁS se debió llamar al método save
        verify(usuarioRepository, times(1)).findByEmail(usuarioBase.getEmail());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    // ==========================================
    // PRUEBAS PARA: obtenerTodos()
    // ==========================================

    @Test
    void obtenerTodos_DeberiaRetornarListaDeUsuarios() {
        // GIVEN: El repositorio tiene una lista con dos usuarios
        Usuario usuario2 = new Usuario(2L, "Ana Lopez", "ana@donaton.com", "admin456", "ADMINISTRADOR");
        List<Usuario> listaMock = List.of(usuarioBase, usuario2);
        
        when(usuarioRepository.findAll()).thenReturn(listaMock);

        // WHEN: Llamamos al servicio
        List<Usuario> resultado = usuarioService.obtenerTodos();

        // THEN: Validamos el tamaño y contenido de la lista
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ana Lopez", resultado.get(1).getNombre());

        // Verificación
        verify(usuarioRepository, times(1)).findAll();
    }

    // ==========================================
    // PRUEBAS PARA: obtenerPorId()
    // ==========================================

    @Test
    void obtenerPorId_CuandoElUsuarioExiste_DeberiaRetornarOptionalConUsuario() {
        // GIVEN: El usuario con ID 1 existe
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioBase));

        // WHEN: Buscamos el usuario
        Optional<Usuario> resultado = usuarioService.obtenerPorId(1L);

        // THEN: Verificamos que esté presente y sea el correcto
        assertTrue(resultado.isPresent());
        assertEquals("Carlos Gomez", resultado.get().getNombre());

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_CuandoElUsuarioNoExiste_DeberiaRetornarOptionalVacio() {
        // GIVEN: El ID 99 no existe en la base de datos
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN: Buscamos el usuario inexistente
        Optional<Usuario> resultado = usuarioService.obtenerPorId(99L);

        // THEN: Verificamos que el Optional venga vacío
        assertTrue(resultado.isEmpty());

        verify(usuarioRepository, times(1)).findById(99L);
    }
}