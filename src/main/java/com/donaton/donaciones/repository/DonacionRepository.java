package com.donaton.donaciones.repository;

import com.donaton.donaciones.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    
    // Buscar el historial de donaciones de un usuario específico
    List<Donacion> findByUsuarioId(Long usuarioId);

    // Una consulta personalizada JPQL para calcular el total recaudado en tiempo real 🌟
    @Query("SELECT SUM(d.monto) FROM Donacion d WHERE d.estado = 'COMPLETADA'")
    Double obtenerTotalRecaudado();
}