package com.vuebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.vuebackend.controllers,com.vuebackend.security")
@EnableJpaRepositories("com.vuebackend.dbrepositories")
@EntityScan("com.vuebackend.entities")
@SpringBootApplication
public class VueBackendRecourcesApplication {

	public static void main(String[] args) {
        SpringApplication.run(VueBackendRecourcesApplication.class, args);
	}
}
