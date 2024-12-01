package com.z1ck.ms_credittracking.repositories;

import com.z1ck.ms_credittracking.entities.CreditTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CreditTrackingRepository extends JpaRepository<CreditTrackingEntity, Long> {
    List<CreditTrackingEntity> findByCreditRequestId(Long creditRequestId);
}
