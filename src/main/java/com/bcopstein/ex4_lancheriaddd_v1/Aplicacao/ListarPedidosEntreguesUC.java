package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna todos os pedidos com status ENTREGUE em um intervalo de datas

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class ListarPedidosEntreguesUC {

  private final PedidoService pedidoService;

  public ListarPedidosEntreguesUC(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  // Busca pedidos com status ENTREGUE criados no período informado
  public List<PedidoResponse> run(LocalDate inicio, LocalDate fim) {
    return pedidoService.listarEntreguesEntreDatas(inicio, fim).stream()
        .map(p -> new PedidoResponse(p, true, "OK", List.of()))
        .toList();
  }
}
