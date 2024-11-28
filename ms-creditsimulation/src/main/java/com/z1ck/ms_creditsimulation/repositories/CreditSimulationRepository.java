package com.z1ck.ms_creditsimulation.repositories;

import com.z1ck.ms_creditsimulation.entities.CreditSimulationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditSimulationRepository extends JpaRepository<CreditSimulationEntity, Long> {
}
