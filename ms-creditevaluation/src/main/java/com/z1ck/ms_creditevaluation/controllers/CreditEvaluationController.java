package com.z1ck.ms_creditevaluation.controllers;

import com.z1ck.ms_creditevaluation.entities.CreditEvaluationEntity;
import com.z1ck.ms_creditevaluation.services.CreditEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit-evaluations")
@CrossOrigin("*")
public class CreditEvaluationController {
    @Autowired
    private CreditEvaluationService creditEvaluationService;

    // Obtener todas las evaluaciones de crédito
    @GetMapping("/")
    public ResponseEntity<List<CreditEvaluationEntity>> listEvaluations() {
        List<CreditEvaluationEntity> evaluations = creditEvaluationService.getEvaluations();
        return ResponseEntity.ok(evaluations);
    }

    // Obtener evaluación de crédito por ID
    @GetMapping("/{id}")
    public ResponseEntity<CreditEvaluationEntity> getEvaluationById(@PathVariable Long id) {
        CreditEvaluationEntity evaluation = creditEvaluationService.getEvaluationById(id);
        if (evaluation != null) {
            return ResponseEntity.ok(evaluation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva evaluación
    @PostMapping("/")
    public ResponseEntity<CreditEvaluationEntity> saveEvaluation(@RequestBody CreditEvaluationEntity evaluation) {
        CreditEvaluationEntity newEvaluation = creditEvaluationService.saveEvaluation(evaluation);
        return ResponseEntity.ok(newEvaluation);
    }

    // Actualizar una evaluación existente
    @PutMapping("/")
    public ResponseEntity<CreditEvaluationEntity> updateEvaluation(@RequestBody CreditEvaluationEntity evaluation) {
        CreditEvaluationEntity updatedEvaluation = creditEvaluationService.updateEvaluation(evaluation);
        return ResponseEntity.ok(updatedEvaluation);
    }

    // Eliminar evaluación de crédito por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluationById(@PathVariable Long id) {
        boolean isDeleted = creditEvaluationService.deleteEvaluation(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Evaluar una solicitud de crédito manualmente
    @PostMapping("/evaluate/{requestId}")
    public ResponseEntity<CreditEvaluationEntity> evaluateCreditRequestManually(@PathVariable Long requestId) {
        CreditEvaluationEntity evaluation = creditEvaluationService.evaluateCreditRequestManually(requestId);
        return ResponseEntity.ok(evaluation);
    }

}
