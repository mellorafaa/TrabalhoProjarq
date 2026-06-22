package com.pizzaria.estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Classe principal de inicialização do Microsserviço de Estoque
 * Responsável por gerenciar o inventário de ingredientes
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstoqueApplication.class, args);
	}

}
