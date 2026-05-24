package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna todos os pedidos cadastrados como lista de respostas

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;

@Component
public class ListarPedidosUC {

  private final PedidoRepository pedidoRepository;

  public ListarPedidosUC(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  // Busca todos os pedidos e os encapsula em PedidoResponse com status aprovado
  public List<PedidoResponse> run() {
    return pedidoRepository.listarTodos().stream()
        .map(p -> new PedidoResponse(p, true, "OK", List.of()))
        .toList();
  }
}
