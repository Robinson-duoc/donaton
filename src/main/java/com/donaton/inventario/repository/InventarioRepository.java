package com.donaton.inventario.repository;

import com.donaton.inventario.model.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<ItemInventario, Long> {
    
    // Buscar ítems filtrados por categoría (ej: "Alimentos")
    List<ItemInventario> findByCategoria(String categoria);
}