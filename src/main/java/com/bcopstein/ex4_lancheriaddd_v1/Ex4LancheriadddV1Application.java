package com.bcopstein.ex4_lancheriaddd_v1;
// Classe Ex4LancheriadddV1Application: responsabilidade principal inferida pelo nome 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//Classe principal de inicialização da aplicação Spring Boot da lancheria
@SpringBootApplication
@ComponentScan("com.bcopstein")
public class Ex4LancheriadddV1Application {

	//Ponto de entrada da aplicação; inicializa o contexto Spring
	public static void main(String[] args) {
		SpringApplication.run(Ex4LancheriadddV1Application.class, args);
	}

}
