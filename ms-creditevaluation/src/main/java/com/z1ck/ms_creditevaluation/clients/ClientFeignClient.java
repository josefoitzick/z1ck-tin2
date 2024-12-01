package com.z1ck.ms_creditevaluation.clients;


import com.z1ck.ms_creditevaluation.configurations.FeignClientConfig;
import com.z1ck.ms_creditevaluation.entities.ClientEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-client", path = "/api/v1/clients", configuration = FeignClientConfig.class)
public interface ClientFeignClient {

    @GetMapping("/{id}")
    ClientEntity getClientById(@PathVariable("id") Long id);
}
