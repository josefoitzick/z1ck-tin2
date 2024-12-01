package com.z1ck.ms_credittotalcost.clients;

import com.z1ck.ms_credittotalcost.entities.CreditRequestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-creditrequest", path = "/api/v1/credit-requests")
public interface CreditRequestFeignClient {
    @GetMapping("/{id}")
    CreditRequestEntity getCreditRequestById(@PathVariable("id") Long id);
}
