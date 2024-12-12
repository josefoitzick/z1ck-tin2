package com.z1ck.ms_credittracking.controllers;

import com.z1ck.ms_credittracking.entities.CreditTrackingEntity;
import com.z1ck.ms_credittracking.services.CreditTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/credit-tracking")
public class CreditTrackingController {
    @Autowired
    private CreditTrackingService creditTrackingService;

    // Endpoint para registrar un cambio de estado
    @PostMapping("/{creditRequestId}/record-status-change")
    public ResponseEntity<CreditTrackingEntity> recordStatusChange(
            @PathVariable Long creditRequestId,
            @RequestParam String previousStatus,
            @RequestParam String newStatus) {
        CreditTrackingEntity trackingRecord = creditTrackingService.recordStatusChange(creditRequestId, previousStatus, newStatus);
        return ResponseEntity.ok(trackingRecord);
    }

    // Endpoint para obtener el historial de una solicitud
    @GetMapping("/{creditRequestId}/history")
    public ResponseEntity<List<CreditTrackingEntity>> getTrackingHistoryByRequestId(@PathVariable Long creditRequestId) {
        List<CreditTrackingEntity> trackingHistory = creditTrackingService.getTrackingHistoryByRequestId(creditRequestId);
        return ResponseEntity.ok(trackingHistory);
    }

}
