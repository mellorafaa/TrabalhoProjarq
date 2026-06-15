package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna os pedidos entregues de um cliente em um intervalo de datas

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class ListarPedidosClienteEntreguesUC {

  private final PedidoService pedidoService;

  public ListarPedidosClienteEntreguesUC(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  // Busca pedidos com status ENTREGUE do cliente no período informado
  public List<PedidoResponse> run(String cpf, LocalDate inicio, LocalDate fim) {
    return pedidoService.listarEntreguesEntreDatasParaCliente(cpf, inicio, fim).stream()
        .map(p -> new PedidoResponse(p, true, "OK", List.of()))
        .toList();
  }
}
