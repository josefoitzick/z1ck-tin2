package com.z1ck.ms_creditevaluation.repositories;

import com.z1ck.ms_creditevaluation.entities.CreditEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditEvaluationRepository extends JpaRepository<CreditEvaluationEntity,Long> {
}
