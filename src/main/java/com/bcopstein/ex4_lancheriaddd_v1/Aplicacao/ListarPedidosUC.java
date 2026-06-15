package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna todos os pedidos cadastrados como lista de respostas

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class ListarPedidosUC {

  private final PedidoService pedidoService;

  public ListarPedidosUC(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  // Busca todos os pedidos e os encapsula em PedidoResponse com status aprovado
  public List<PedidoResponse> run() {
    return pedidoService.listarTodos().stream()
        .map(p -> new PedidoResponse(p, true, "OK", List.of()))
        .toList();
  }
}
