package com.z1ck.ms_creditevaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCreditevaluationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditevaluationApplication.class, args);
	}

}
