package com.donaton.inventario;

import com.donaton.inventario.controller.InventarioController;
import com.donaton.inventario.service.InventarioService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

@Autowired
MockMvc mvc;

@MockBean
InventarioService service;

@Test
void abrirBodega() throws Exception {

when(
service.obtenerTodo()
)

.thenReturn(
List.of()
);

mvc.perform(

get(
"/inventario/bodega"
)

)

.andExpect(

status().isOk()

)

.andExpect(

view().name(
"panel-inventario"
)

);

}

}