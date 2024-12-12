package com.z1ck.ms_credittotalcost.controllers;


import com.z1ck.ms_credittotalcost.entities.CreditTotalCostEntity;
import com.z1ck.ms_credittotalcost.services.CreditTotalCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credit-total-cost")
public class CreditTotalCostController {
    @Autowired
    private CreditTotalCostService creditTotalCostService;

    // Endpoint para calcular y guardar el costo total de la solicitud de crédito
    @PostMapping("/calculate/{creditRequestId}")
    public ResponseEntity<CreditTotalCostEntity> calculateAndSaveTotalCost(@PathVariable Long creditRequestId) {
        CreditTotalCostEntity totalCostEntity = creditTotalCostService.calculateAndSaveTotalCost(creditRequestId);
        return ResponseEntity.ok(totalCostEntity);
    }

    // Endpoint para obtener el costo total ya calculado de una solicitud de crédito
    @GetMapping("/{creditRequestId}")
    public ResponseEntity<CreditTotalCostEntity> getTotalCostByRequestId(@PathVariable Long creditRequestId) {
        try {
            CreditTotalCostEntity totalCostEntity = creditTotalCostService.calculateAndSaveTotalCost(creditRequestId);
            return ResponseEntity.ok(totalCostEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
