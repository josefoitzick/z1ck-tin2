package com.z1ck.ms_credittotalcost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCredittotalcostApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCredittotalcostApplication.class, args);
	}

}
