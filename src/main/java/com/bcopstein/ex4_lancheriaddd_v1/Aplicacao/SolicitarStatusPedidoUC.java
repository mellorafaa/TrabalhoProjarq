package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que consulta e retorna o ID e o status atual de um pedido pelo ID

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoStatusResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class SolicitarStatusPedidoUC {

  private final PedidoService pedidoService;

  public SolicitarStatusPedidoUC(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  // Busca o pedido pelo ID e retorna id e status; retorna null se não encontrado
  public PedidoStatusResponse run(long pedidoId) {
    Pedido pedido = pedidoService.recuperarPorId(pedidoId);
    if (pedido == null) return null;
    return new PedidoStatusResponse(pedido.getId(), pedido.getStatus().name());
  }
}
