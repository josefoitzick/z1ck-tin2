package com.z1ck.ms_creditrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCreditrequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditrequestApplication.class, args);
	}

}
