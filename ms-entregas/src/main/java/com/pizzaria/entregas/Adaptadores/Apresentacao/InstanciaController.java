package com.pizzaria.entregas.Adaptadores.Apresentacao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

/**
 * Endpoint de diagnostico para visualizar qual instancia do ms-entregas
 * respondeu a requisicao (essencial para validar load balancer ativo).
 *
 * GET /api/v1/entregas/instancia
 */
@RestController
@RequestMapping("/entregas")
@Slf4j
public class InstanciaController {

    private final AtomicLong contadorRequisicoes = new AtomicLong(0);

    @Value("${server.port}")
    private String porta;

    @Value("${spring.application.name}")
    private String nomeServico;

    @GetMapping("/instancia")
    public Map<String, Object> infoInstancia() {
        long n = contadorRequisicoes.incrementAndGet();
        String hostname = hostnameLocal();
        log.info(">>> [{}] respondeu requisicao #{} (host={}, porta={})",
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
