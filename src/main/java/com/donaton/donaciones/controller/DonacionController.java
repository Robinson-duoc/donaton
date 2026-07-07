package com.donaton.donaciones.controller;

import com.donaton.donaciones.model.Donacion;
import com.donaton.donaciones.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@CrossOrigin(origins = "*") // Permite la conexión con tu futuro Frontend en NPM
public class DonacionController {

    @Autowired
    private DonacionService donacionService;

    // 1. POST: Registrar una nueva donación monetaria
    @PostMapping
    public ResponseEntity<?> crearDonacion(@RequestBody Donacion donacion) {
        try {
            Donacion nuevaDonacion = donacionService.registrarDonacion(donacion);
            return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si el usuario no existe o el monto es <= 0, mandamos un Bad Request (400)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2. GET: Obtener el pozo total acumulado de la Donatón 💰
    @GetMapping("/total")
    public ResponseEntity<Double> obtenerTotalAcumulado() {
        return new ResponseEntity<>(donacionService.obtenerTotalDonado(), HttpStatus.OK);
    }

    // 3. GET: Historial global de todas las donaciones realizadas
    @GetMapping
    public ResponseEntity<List<Donacion>> listarDonaciones() {
        return new ResponseEntity<>(donacionService.obtenerTodas(), HttpStatus.OK);
    }
}