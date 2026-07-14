package com.iadv.cti.avaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CtiAvayaIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CtiAvayaIntegrationApplication.class, args);
		System.out.println("=".repeat(60));
		System.out.println("CTI Avaya Integration - IADV");
		System.out.println("Technical Test - FullStack Senior");
		System.out.println("=".repeat(60));
		System.out.println("WebSocket conectándose al Mock CTI Server...");
		System.out.println("API disponible en: http://localhost:8080/api/cti");
		System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
		System.out.println("=".repeat(60));
	}
}
