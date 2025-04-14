package com.contas107;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Contas107 API", version = "1.0", description = "API documentation for Contas107"))
public class Contas107Application {

	public static void main(String[] args) {
		SpringApplication.run(Contas107Application.class, args);
	}

}
