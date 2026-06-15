package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que cancela um pedido aprovado; rejeita se o status atual não permitir cancelamento

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CancelarPedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class CancelarPedidoUC {

  private final PedidoService pedidoService;

  public CancelarPedidoUC(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  // Tenta cancelar o pedido pelo ID; retorna false com motivo se o cancelamento não for permitido
  public CancelarPedidoResponse run(long idPedido) {

    Pedido pedido = pedidoService.recuperarPorId(idPedido);
    if (pedido == null) {
      return new CancelarPedidoResponse(
        false,
        "Pedido não encontrado com o id: " + idPedido,
        idPedido
      );
    }

    if (pedido.getStatus() != Pedido.Status.APROVADO) {
      return new CancelarPedidoResponse(
        false,
        construirMotivoRejeicao(pedido.getStatus()),
        idPedido
      );
    }

    pedido.cancelar();
    pedidoService.atualizarStatus(pedido.getId(), pedido.getStatus());

    return new CancelarPedidoResponse(
      true,
      "Pedido " + idPedido + " cancelado com sucesso.",
      idPedido
    );
  }

  // Retorna mensagem de rejeição específica para cada status que impede o cancelamento
  private String construirMotivoRejeicao(Pedido.Status statusAtual) {
    return switch (statusAtual) {
      case PAGO ->
        "Pedido já foi pago e não pode ser cancelado.";
      case CANCELADO ->
        "Pedido já está cancelado.";
      case NOVO ->
        "Pedido ainda não foi aprovado (status: NOVO).";
      default ->
        "Pedido não pode ser cancelado. Status atual: " + statusAtual + ".";
    };
  }
}
