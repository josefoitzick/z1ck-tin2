package com.z1ck.ms_creditrequest.clients;


import com.z1ck.ms_creditrequest.configurations.FeignClientConfig;
import com.z1ck.ms_creditrequest.entities.CreditEntity;
import com.z1ck.ms_creditrequest.entities.CreditRequestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ms-credit", path = "/api/v1/credits", configuration = FeignClientConfig.class)
public interface CreditServiceFeignClient {

    /*
    CreditRequest en sus servicios ocupa de un solo microservicio externo,
    que es el de credit, dado que cuando se apreta aprobar se crea el credito,
    por medio del metodo creditService.createCreditFromRequest(request)
     */

    // Crear crédito a partir de una solicitud
    @PostMapping("/create-from-request")
    CreditEntity createCreditFromRequest(@RequestBody CreditRequestEntity creditRequest);

    // Guardar un crédito en el repositorio de ms-credit
    @PostMapping("/")
    CreditEntity saveCredit(@RequestBody CreditEntity credit);
}
