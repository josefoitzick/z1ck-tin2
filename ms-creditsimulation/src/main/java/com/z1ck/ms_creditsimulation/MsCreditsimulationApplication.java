package com.z1ck.ms_creditsimulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class MsCreditsimulationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditsimulationApplication.class, args);
	}

}
