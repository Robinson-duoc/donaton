package com.donaton.inventario.service;

import com.donaton.inventario.model.ItemInventario;
import com.donaton.inventario.repository.InventarioRepository;
import com.donaton.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Inyectamos el servicio de usuarios para validar la existencia del donante
    @Autowired
    private UsuarioService usuarioService;

    // 1. Registrar un nuevo ítem en el inventario
    public ItemInventario registrarItem(ItemInventario item) {
        // Validación: Verificar que el usuario exista
        if (usuarioService.obtenerPorId(item.getUsuarioId()).isEmpty()) {
            throw new RuntimeException("Error: El ID de usuario proporcionado no existe.");
        }

        if (item.getCantidad() == null || item.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad del producto debe ser mayor a cero.");
        }

        return inventarioRepository.save(item);
    }

    // 2. Obtener toda la lista de productos en bodega
    public List<ItemInventario> obtenerTodo() {
        return inventarioRepository.findAll();
    }
}