package com.iss4u.erp.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ERPSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ERPSchedulerServiceApplication.class, args);
	}

}
