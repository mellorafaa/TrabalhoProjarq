package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que cancela um pedido aprovado

// Permite cancelamento APENAS de pedidos no status APROVADO
// Rejeita se o status atual não permitir cancelamento
// Orquestra:
//   1. Busca do pedido pelo ID
//   2. Validação de status (deve estar APROVADO)
//   3. Atualização de status para CANCELADO

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

  // Tenta cancelar o pedido pelo ID
  // Retorna sucesso se cancelado, erro se o status não permite cancelamento
  public CancelarPedidoResponse run(long idPedido) {

    if (idPedido <= 0) {
      return new CancelarPedidoResponse(
          false,
          "ID do pedido inválido: " + idPedido + ". Deve ser maior que zero",
          idPedido);
    }

    try {
      Pedido pedido = pedidoService.recuperarPorId(idPedido);
      if (pedido == null) {
        return new CancelarPedidoResponse(
            false,
            "Pedido não encontrado com o id: " + idPedido,
            idPedido);
      }

      // Permite cancelamento APENAS de pedidos no status APROVADO
      if (pedido.getStatus() != Pedido.Status.APROVADO) {
        return new CancelarPedidoResponse(
            false,
            construirMotivoRejeicao(pedido.getStatus()),
            idPedido);
      }

      // Atualiza status para CANCELADO
      pedido.cancelar();
      pedidoService.atualizarStatus(pedido.getId(), pedido.getStatus());

      return new CancelarPedidoResponse(
          true,
          "Pedido " + idPedido + " cancelado com sucesso.",
          idPedido);

    } catch (IllegalArgumentException e) {
      // Erros de validação
      return new CancelarPedidoResponse(
          false,
          "Erro de validação: " + e.getMessage(),
          idPedido);
    } catch (RuntimeException e) {
      // Outros erros inesperados
      return new CancelarPedidoResponse(
          false,
          "Erro ao cancelar pedido: " + e.getMessage(),
          idPedido);
    }
  }

  // Retorna mensagem de rejeição específica para cada status que impede o
  // cancelamento
  private String construirMotivoRejeicao(Pedido.Status statusAtual) {
    return switch (statusAtual) {
      case NOVO ->
        "Pedido ainda não foi aprovado (status: NOVO). Apenas pedidos aprovados podem ser cancelados.";
      case PAGO ->
        "Pedido já foi pago e não pode ser cancelado. Pedidos pagos estão em preparação na cozinha.";
      case CANCELADO ->
        "Pedido já está cancelado. Não é possível cancelar novamente.";
      case AGUARDANDO, PREPARACAO, PRONTO, TRANSPORTE, ENTREGUE ->
        "Pedido não pode ser cancelado (status: " + statusAtual + "). Está em processo de entrega ou já foi entregue.";
      default ->
        "Pedido não pode ser cancelado. Status atual: " + statusAtual + ".";
    };
  }
}
