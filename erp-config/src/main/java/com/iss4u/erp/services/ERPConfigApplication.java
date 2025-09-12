package com.iss4u.erp.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ERPConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ERPConfigApplication.class, args);
	}

}
