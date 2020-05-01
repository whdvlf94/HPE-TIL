package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class SecondBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondBootProjectApplication.class, args);
	}

}
