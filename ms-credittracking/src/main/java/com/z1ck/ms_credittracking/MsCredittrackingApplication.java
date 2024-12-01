package com.z1ck.ms_credittracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCredittrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCredittrackingApplication.class, args);
	}

}
