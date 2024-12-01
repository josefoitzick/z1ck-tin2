package com.z1ck.ms_credit.repositories;

import com.z1ck.ms_credit.entitites.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
    List<CreditEntity> findByClientId(Long clientId);
}
