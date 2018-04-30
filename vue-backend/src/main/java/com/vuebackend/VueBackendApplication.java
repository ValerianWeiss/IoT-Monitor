package com.vuebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.controllers")
@EnableJpaRepositories("com.dbrepositories")
@EntityScan("com.entities")
@SpringBootApplication
public class VueBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VueBackendApplication.class, args);
	}
}
