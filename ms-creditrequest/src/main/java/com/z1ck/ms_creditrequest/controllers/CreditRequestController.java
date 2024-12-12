package com.z1ck.ms_creditrequest.controllers;

import com.z1ck.ms_creditrequest.entities.CreditRequestEntity;
import com.z1ck.ms_creditrequest.services.CreditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit-requests")
public class CreditRequestController {
    @Autowired
    private CreditRequestService creditRequestService;

    // Obtener todas las solicitudes de crédito
    @GetMapping("/")
    public ResponseEntity<List<CreditRequestEntity>> listRequests() {
        List<CreditRequestEntity> requests = creditRequestService.getRequests();
        return ResponseEntity.ok(requests);
    }

    // Obtener solicitud de crédito por ID
    @GetMapping("/{id}")
    public ResponseEntity<CreditRequestEntity> getRequestById(@PathVariable Long id) {
        CreditRequestEntity request = creditRequestService.getRequestById(id);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar una solicitud existente
    @PutMapping("/")
    public ResponseEntity<CreditRequestEntity> updateRequest(@RequestBody CreditRequestEntity request) {
        CreditRequestEntity updatedRequest = creditRequestService.updateRequest(request);
        return ResponseEntity.ok(updatedRequest);
    }

    // Eliminar solicitud de crédito por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestById(@PathVariable Long id) {
        boolean isDeleted = creditRequestService.deleteRequest(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //##############################
    //LOGICA DE NEGOCIOS, esto esta en services pero estos controllers especificos estan muy enlazados
    //##############################
    // Crear una nueva solicitud de crédito

    // Obtener todas las solicitudes de crédito de un cliente por su clientId
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CreditRequestEntity>> getRequestsByClientId(@PathVariable Long clientId) {
        List<CreditRequestEntity> requests = creditRequestService.getRequestsByClientId(clientId);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CreditRequestEntity> updateStatus(
            @PathVariable Long id, @RequestParam String newStatus) {
        CreditRequestEntity updatedRequest = creditRequestService.updateStatus(id, newStatus);
        return ResponseEntity.ok(updatedRequest);
    }



    // Crear una nueva solicitud de crédito sin evaluación automática
    @PostMapping("/")
    public ResponseEntity<CreditRequestEntity> createCreditRequest(@RequestBody CreditRequestEntity request) {
        CreditRequestEntity newRequest = creditRequestService.createCreditRequest(request);
        return ResponseEntity.ok(newRequest);
    }

    //ARCHIVO
    // Endpoint para subir un documento
    @PostMapping("/{id}/upload-document")
    public ResponseEntity<String> uploadDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            creditRequestService.saveDocument(id, file);
            return ResponseEntity.ok("Archivo subido exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir archivo");
        }
    }

    // Endpoint para descargar/ver el documento
    @GetMapping("/{id}/document")
    public ResponseEntity<byte[]> getDocument(@PathVariable Long id) {
        byte[] document = creditRequestService.getDocument(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=document.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(document);
    }
}
