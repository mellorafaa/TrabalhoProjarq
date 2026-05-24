package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;
// Classe Controller: responsabilidade principal inferida pelo nome 

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
  @GetMapping("/api/welcome")
  @CrossOrigin("*")
  // Método welcomeMessage: public welcomeMessage — descrição breve 
  public String welcomeMessage() {
    return "Bem Vindo a Pizzaria ECA";
  }
}
