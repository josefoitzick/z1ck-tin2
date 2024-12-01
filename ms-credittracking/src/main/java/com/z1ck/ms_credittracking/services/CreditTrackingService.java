package com.z1ck.ms_credittracking.services;

import com.z1ck.ms_credittracking.entities.CreditTrackingEntity;
import com.z1ck.ms_credittracking.repositories.CreditTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditTrackingService {
    @Autowired
    private CreditTrackingRepository creditTrackingRepository;

    // Método para registrar un cambio de estado
    public CreditTrackingEntity recordStatusChange(Long creditRequestId, String previousStatus, String newStatus) {
        CreditTrackingEntity trackingRecord = new CreditTrackingEntity();
        trackingRecord.setCreditRequestId(creditRequestId);
        trackingRecord.setPreviousStatus(previousStatus);
        trackingRecord.setNewStatus(newStatus);
        trackingRecord.setChangeDate(LocalDateTime.now());

        return creditTrackingRepository.save(trackingRecord);
    }

    // Método para obtener el historial de seguimiento de una solicitud
    public List<CreditTrackingEntity> getTrackingHistoryByRequestId(Long creditRequestId) {
        return creditTrackingRepository.findByCreditRequestId(creditRequestId);
    }
}
