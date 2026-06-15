package com.donaton.usuarios;
import com.donaton.usuarios.controller.UsuarioController;
import com.donaton.usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

@Autowired
MockMvc mvc;

@MockBean
UsuarioService service;

@Test
void debeMostrarRegistro() throws Exception {

when(
service.obtenerTodos()
)

.thenReturn(
List.of()
);

mvc.perform(
get("/usuarios/registro")
)

.andExpect(
status().isOk()
)

.andExpect(
view().name(
"registro-usuarios"
)
);

}

}