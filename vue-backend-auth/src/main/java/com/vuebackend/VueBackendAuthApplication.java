package com.vuebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.vuebackend.controllers, com.vuebackend.communication, com.vuebackend.jwt")
@EntityScan("com.vuebackend.entities")
@SpringBootApplication
public class VueBackendAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(VueBackendAuthApplication.class, args);
	}
}
