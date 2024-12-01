package com.z1ck.ms_creditcosttotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCreditcosttotalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditcosttotalApplication.class, args);
	}

}
