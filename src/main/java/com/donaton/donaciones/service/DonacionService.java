package com.donaton.donaciones.service;

import com.donaton.donaciones.model.Donacion;
import com.donaton.donaciones.repository.DonacionRepository;
import com.donaton.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository donacionRepository;

    // Inyectamos el servicio del otro módulo (simulando una llamada API interna entre microservicios)
    @Autowired
    private UsuarioService usuarioService;

    // 1. Registrar una nueva donación
    public Donacion registrarDonacion(Donacion donacion) {
        // CORREGIDO: Cambiado de 'obtainPorId' a 'obtenerPorId'
        if (usuarioService.obtenerPorId(donacion.getUsuarioId()).isEmpty()) {
            throw new RuntimeException("Error: El ID de usuario proporcionado no existe.");
        }
        
        if (donacion.getMonto() <= 0) {
            throw new RuntimeException("El monto de la donación debe ser mayor a cero.");
        }

        return donacionRepository.save(donacion);
    }

    // 2. Obtener el pozo total acumulado de la Donatón
    public Double obtenerTotalDonado() {
        // CORREGIDO: Cambiado de 'obtainTotalRecaudado' a 'obtenerTotalRecaudado'
        Double total = donacionRepository.obtenerTotalRecaudado();
        return (total != null) ? total : 0.0;
    }

    // 3. Obtener todas las donaciones para el historial global
    public List<Donacion> obtenerTodas() {
        return donacionRepository.findAll();
    }
}