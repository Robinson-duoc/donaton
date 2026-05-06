package com.donaton.controller;

import com.donaton.dto.ProductoDTO;
import com.donaton.model.Producto;
import com.donaton.service.ProductoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping
    public Producto guardarProducto(@RequestBody ProductoDTO dto) {

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());

        return productoService.guardarProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}