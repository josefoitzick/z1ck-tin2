package com.z1ck.ms_creditsimulation.controllers;

import com.z1ck.ms_creditsimulation.entities.CreditSimulationEntity;
import com.z1ck.ms_creditsimulation.services.CreditSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credit-simulation")
public class CreditSimulationController {

    @Autowired
    private CreditSimulationService creditSimulationService;

    @PostMapping("/simulate")
    public ResponseEntity<Double> simulateCredit(@RequestBody CreditSimulationEntity request) {
        // Usar directamente el objeto CreditSimulationEntity recibido desde el frontend

        // Calcular la cuota mensual usando el servicio y guardar la simulación
        double monthlyPayment = creditSimulationService.simulateMonthlyPayment(request);

        return ResponseEntity.ok(monthlyPayment);  // Devolver la cuota mensual calculada
    }
}
