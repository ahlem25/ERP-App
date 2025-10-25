package com.iss4u.erp.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iss4u.erp.services"})
public class ERPUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ERPUserServiceApplication.class, args);
	}

}
