package com.z1ck.ms_credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditApplication.class, args);
	}

}
