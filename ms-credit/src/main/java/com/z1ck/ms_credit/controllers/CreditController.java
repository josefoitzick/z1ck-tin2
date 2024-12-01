package com.z1ck.ms_credit.controllers;

import com.z1ck.ms_credit.entitites.CreditEntity;
import com.z1ck.ms_credit.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/credits")
@CrossOrigin("*")
public class CreditController {
    @Autowired
    private CreditService creditService;

    // Obtener todos los créditos
    @GetMapping("/")
    public ResponseEntity<List<CreditEntity>> listCredits() {
        List<CreditEntity> credits = creditService.getCredits();
        return ResponseEntity.ok(credits);
    }

    // Obtener un crédito por ID
    @GetMapping("/{id}")
    public ResponseEntity<CreditEntity> getCreditById(@PathVariable Long id) {
        CreditEntity credit = creditService.getCreditById(id);
        if (credit != null) {
            return ResponseEntity.ok(credit);
        } else {
            return ResponseEntity.notFound().build();  // Si no se encuentra el crédito
        }
    }

    // Crear un nuevo crédito
    @PostMapping("/")
    public ResponseEntity<CreditEntity> saveCredit(@RequestBody CreditEntity credit) {
        CreditEntity newCredit = creditService.saveCredit(credit);
        return ResponseEntity.ok(newCredit);
    }

    // Actualizar un crédito existente
    @PutMapping("/")
    public ResponseEntity<CreditEntity> updateCredit(@RequestBody CreditEntity credit) {
        CreditEntity updatedCredit = creditService.updateCredit(credit);
        return ResponseEntity.ok(updatedCredit);
    }

    // Eliminar un crédito por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditById(@PathVariable Long id) {
        boolean isDeleted = creditService.deleteCredit(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //######################
    //LOGICA DE NEGOCIOS
    //######################
    // Obtener todos los créditos de un cliente por clientId
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CreditEntity>> getCreditsByClientId(@PathVariable Long clientId) {
        List<CreditEntity> credits = creditService.getCreditsByClientId(clientId);
        return ResponseEntity.ok(credits);
    }

}
