package com.z1ck.ms_creditevaluation.clients;

import com.z1ck.ms_creditevaluation.configurations.FeignClientConfig;
import com.z1ck.ms_creditevaluation.entities.CreditRequestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "ms-creditrequest", path = "/api/v1/credit-requests", configuration = FeignClientConfig.class)
public interface CreditRequestFeignClient {

    @GetMapping("/{id}")
    CreditRequestEntity getCreditRequestById(@PathVariable("id") Long id);
    @PutMapping("/")
    CreditRequestEntity updateCreditRequest(@RequestBody CreditRequestEntity creditRequest);
}
