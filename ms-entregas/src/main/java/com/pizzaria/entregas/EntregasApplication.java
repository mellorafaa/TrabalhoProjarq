package com.pizzaria.entregas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Classe principal de inicialização do Microsserviço de Entregas
 * Responsável por gerenciar a entrega de pedidos
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EntregasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntregasApplication.class, args);
	}

}
