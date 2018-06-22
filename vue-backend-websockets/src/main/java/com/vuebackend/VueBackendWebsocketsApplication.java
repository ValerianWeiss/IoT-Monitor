package com.vuebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.vuebackend.controllers, com.vuebackend.communication")
@SpringBootApplication
public class VueBackendWebsocketsApplication {
	public static void main(String[] args) {
        SpringApplication.run(VueBackendWebsocketsApplication.class, args);
	}
}
