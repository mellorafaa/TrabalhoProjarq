package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;
// Controller REST simples que expõe a rota GET /api/welcome com mensagem de boas-vindas da lancheria

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
  @GetMapping("/api/welcome")
  @CrossOrigin("*")
  // Retorna a mensagem de boas-vindas da API da lancheria
  public String welcomeMessage() {
    return "Bem Vindo a Pizzaria ECA";
  }
}
