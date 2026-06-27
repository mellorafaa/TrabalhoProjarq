package com.bcopstein.ex4_lancheriaddd_v1.Configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	// RestTemplate para chamar o ms-estoque (comunicacao sincrona).
	// O load balancing entre as N replicas de ms-estoque e feito pelo
	// DNS round-robin nativo do Docker Compose: o nome "ms-estoque"
	// resolve para TODOS os IPs das replicas e o resolver escolhe um
	// a cada lookup. Esta abordagem e mais simples e robusta do que
	// usar Spring Cloud LoadBalancer bloqueante (que pode interagir
	// mal com o Spring MVC + Reactor schedulers internos em algumas
	// versoes).
	//
	// Timeouts curtos garantem que, se uma replica estiver lenta ou
	// fora do ar, o sistema falha rapidamente em vez de pendurar.
	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5_000);
		factory.setReadTimeout(10_000);
		return new RestTemplate(factory);
	}
}
