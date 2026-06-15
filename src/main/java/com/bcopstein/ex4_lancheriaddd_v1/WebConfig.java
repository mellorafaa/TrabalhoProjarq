package com.bcopstein.ex4_lancheriaddd_v1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Autenticação JWT foi movida para o API Gateway (arquitetura de microsserviços).
// O monolito confia que qualquer requisição que chegue aqui já foi autenticada pelo gateway.
@Configuration
public class WebConfig implements WebMvcConfigurer {
}
