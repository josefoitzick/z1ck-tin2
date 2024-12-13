package com.z1ck.ms_creditrequest.services;


import com.z1ck.ms_creditrequest.clients.CreditServiceFeignClient;
import com.z1ck.ms_creditrequest.entities.CreditEntity;
import com.z1ck.ms_creditrequest.entities.CreditRequestEntity;
import com.z1ck.ms_creditrequest.repositories.CreditRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CreditRequestService {
    @Autowired
    private CreditRequestRepository creditRequestRepository;

    @Autowired
    private CreditServiceFeignClient creditServiceFeignClient;

    // Obtener todas las solicitudes de crédito
    public List<CreditRequestEntity> getRequests() {
        return creditRequestRepository.findAll();
    }

    // Obtener solicitud de crédito por ID
    public CreditRequestEntity getRequestById(Long id) {
        Optional<CreditRequestEntity> request = creditRequestRepository.findById(id);
        return request.orElse(null);
    }

    // Actualizar una solicitud existente
    public CreditRequestEntity updateRequest(CreditRequestEntity request) {
        return creditRequestRepository.save(request);
    }

    // Eliminar una solicitud de crédito por ID
    public boolean deleteRequest(Long id) {
        if (creditRequestRepository.existsById(id)) {
            creditRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtener todas las solicitudes de crédito de un cliente
    public List<CreditRequestEntity> getRequestsByClientId(Long clientId) {
        return creditRequestRepository.findByClientId(clientId);
    }

    // Crear una nueva solicitud de crédito
    public CreditRequestEntity createCreditRequest(CreditRequestEntity request) {
        request.setStatus("En Revisión");
        return creditRequestRepository.save(request);
    }

    // Actualizar el estado de la solicitud, usando Feign para crear el crédito si es aprobado
    public CreditRequestEntity updateStatus(Long requestId, String newStatus) {
        CreditRequestEntity request = creditRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Verificar si la solicitud ya ha sido rechazada para bloquear más cambios
        if ("Rechazada".equals(request.getStatus())) {
            throw new IllegalStateException("No se puede cambiar el estado de una solicitud rechazada.");
        }

        // Actualizar el estado de la solicitud
        request.setStatus(newStatus);
        CreditRequestEntity updatedRequest = creditRequestRepository.save(request);

        // Si el nuevo estado es "Aprobada", crear el crédito utilizando Feign Client
        if ("Aprobada".equals(newStatus)) {
            CreditEntity credito = creditServiceFeignClient.createCreditFromRequest(request);
            // Guardar el crédito en el repositorio de ms-credit
            CreditEntity savedCredit = creditServiceFeignClient.saveCredit(credito);

            if (savedCredit != null) {
                System.out.println("Crédito guardado exitosamente: " + savedCredit);
            } else {
                throw new RuntimeException("Error al guardar el crédito en ms-credit");
            }
        }

        return updatedRequest;
    }

    // Método para subir el documento
    public void saveDocument(Long requestId, MultipartFile file) throws IOException {
        CreditRequestEntity request = creditRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        request.setDocument(file.getBytes());
        creditRequestRepository.save(request);
    }

    // Método para obtener el documento
    public byte[] getDocument(Long requestId) {
        CreditRequestEntity request = creditRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        return request.getDocument();
    }

}
