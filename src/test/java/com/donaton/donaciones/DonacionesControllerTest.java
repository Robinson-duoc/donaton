package com.donaton.donaciones;

import com.donaton.donaciones.controller.DonacionesController;
import com.donaton.donaciones.service.DonacionService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DonacionesController.class)
class DonacionesControllerTest {

@Autowired
MockMvc mvc;

@MockBean
DonacionService service;

@Test
void abrirPanelDonaciones()
throws Exception {

when(
service.obtenerTodas()
)

.thenReturn(
List.of()
);

mvc.perform(

get(
"/donaciones/registro"
)

)

.andExpect(

status().isOk()

)

.andExpect(

view().name(
"donaciones"
)

);

}

}