package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint de diagnostico para visualizar qual instancia do pizzaria-service
 * respondeu a requisicao. Essencial para validar que o gateway esta
 * fazendo load balancing entre as N replicas do monolito.
 *
 * GET /instancia  (via gateway: GET http://localhost:8080/instancia)
 */
@RestController
@RequestMapping("/instancia")
@CrossOrigin("*")
public class InstanciaController {

  private final AtomicLong contadorRequisicoes = new AtomicLong(0);

  @Value("${server.port}")
  private String porta;

  @Value("${spring.application.name}")
  private String nomeServico;

  @GetMapping
  public Map<String, Object> infoInstancia() {
    long n = contadorRequisicoes.incrementAndGet();
    String hostname = hostnameLocal();
    System.out.printf(">>> [%s] respondeu requisicao #%d (host=%s, porta=%s)%n",
        nomeServico, n, hostname, porta);
    return Map.of(
        "servico", nomeServico,
        "hostname", hostname,
        "porta", porta,
        "requisicoesAtendidas", n
    );
  }

  private static String hostnameLocal() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      return "desconhecido";
    }
  }
}
