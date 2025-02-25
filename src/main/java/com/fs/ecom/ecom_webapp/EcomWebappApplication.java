package com.fs.ecom.ecom_webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@ComponentScan("com.fs")
public class EcomWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomWebappApplication.class, args);
	}

}
