package com.z1ck.ms_creditrequest.repositories;

import com.z1ck.ms_creditrequest.entities.CreditRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequestEntity,Long> {
    // Definir método para obtener solicitudes de un cliente por su ID
    List<CreditRequestEntity> findByClientId(Long clientId);
}
