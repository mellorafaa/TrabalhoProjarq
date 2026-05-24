package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna todos os pedidos de um cliente específico pelo CPF

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;

@Component
public class ListarPedidosClienteUC {

  private final PedidoRepository pedidoRepository;

  public ListarPedidosClienteUC(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  // Busca os pedidos do cliente pelo CPF; lança exceção se o CPF for nulo ou vazio
  public List<PedidoResponse> run(String clienteCpf) {
    if (clienteCpf == null || clienteCpf.isBlank()) {
      throw new IllegalArgumentException("CPF do cliente não pode estar vazio");
    }
    return pedidoRepository.listarPorClienteCpf(clienteCpf).stream()
        .map(p -> new PedidoResponse(p, true, "OK", List.of()))
        .toList();
  }
}
