package com.iss4u.erp.services;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iss4u.erp.services", "com.iss4u.erp.services.modules"})
public class ERPClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ERPClientServiceApplication.class, args);
    }

}