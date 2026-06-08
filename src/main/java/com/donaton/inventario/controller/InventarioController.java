package com.donaton.inventario.controller;

import com.donaton.inventario.model.ItemInventario;
import com.donaton.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*") // Evita bloqueos de seguridad de CORS con el Frontend
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // 1. POST: Registrar la entrada de un producto material a bodega
    @PostMapping
    public ResponseEntity<?> registrarEntradaBodega(@RequestBody ItemInventario item) {
        try {
            ItemInventario nuevoItem = inventarioService.registrarItem(item);
            return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2. GET: Listar todo el stock actual que hay en la bodega
    @GetMapping
    public ResponseEntity<List<ItemInventario>> listarStock() {
        return new ResponseEntity<>(inventarioService.obtenerTodo(), HttpStatus.OK);
    }
}