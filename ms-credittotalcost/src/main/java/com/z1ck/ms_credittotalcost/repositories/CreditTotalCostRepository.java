package com.z1ck.ms_credittotalcost.repositories;

import com.z1ck.ms_credittotalcost.entities.CreditTotalCostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditTotalCostRepository extends JpaRepository<CreditTotalCostEntity,Long> {
    // Método para obtener el costo total de una solicitud de crédito por su ID
    List<CreditTotalCostEntity> findByCreditRequestId(Long creditRequestId);
}
