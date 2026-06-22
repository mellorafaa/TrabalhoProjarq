package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que processa o pagamento de um pedido aprovado e o encaminha para a cozinha

// Orquestra:
//   1. Busca do pedido pelo ID
//   2. Validação de status (deve estar APROVADO)
//   3. Processamento do pagamento via serviço de pagamento
//   4. Atualização de status para PAGO
//   5. Registro de data/hora do pagamento
//   6. Envio para a fila da cozinha

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PagarPedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ICozinhaService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEntregaNotificacaoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IPagamentoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class PagarPedidoUC {

  private final PedidoService pedidoService;
  private final IPagamentoService pagamentoService;
  private final ICozinhaService cozinhaService;
  private final IEntregaNotificacaoService entregaNotificacaoService;

  public PagarPedidoUC(PedidoService pedidoService,
      IPagamentoService pagamentoService,
      ICozinhaService cozinhaService,
      IEntregaNotificacaoService entregaNotificacaoService) {
    this.pedidoService = pedidoService;
    this.pagamentoService = pagamentoService;
    this.cozinhaService = cozinhaService;
    this.entregaNotificacaoService = entregaNotificacaoService;
  }

  // Processa o pagamento pelo ID do pedido
  // Retorna sucesso se autorizado e pedido encaminhado à cozinha, erro caso
  // contrário
  public PagarPedidoResponse run(long idPedido) {

    if (idPedido <= 0) {
      return new PagarPedidoResponse(
          false,
          "ID do pedido inválido: " + idPedido + ". Deve ser maior que zero",
          idPedido,
          null);
    }

    try {
      Pedido pedido = pedidoService.recuperarPorId(idPedido);
      if (pedido == null) {
        return new PagarPedidoResponse(
            false,
            "Pedido não encontrado com o id: " + idPedido,
            idPedido,
            null);
      }

      // Verifica se o pedido está no status APROVADO (único status válido para
      // pagamento)
      if (pedido.getStatus() != Pedido.Status.APROVADO) {
        return new PagarPedidoResponse(
            false,
            construirMotivoRejeicao(pedido.getStatus()),
            idPedido,
            pedido.getStatus().name());
      }

      // Processa o pagamento via serviço de pagamento
      boolean autorizado = pagamentoService.processarPagamento(pedido);
      if (!autorizado) {
        return new PagarPedidoResponse(
            false,
            "Pagamento não autorizado para o pedido " + idPedido + ". Verifique saldo/limite.",
            idPedido,
            pedido.getStatus().name());
      }

      // Atualiza status do pedido para PAGO
      pedido.pagar();

      // Registra data e hora do pagamento
      pedidoService.registrarPagamento(idPedido, LocalDateTime.now());

      // Encaminha pedido para a fila da cozinha
      cozinhaService.chegadaDePedido(pedido);

      // Notifica ms-entregas via fila para iniciar a entrega
      entregaNotificacaoService.notificarPedidoPago(
          pedido.getId(),
          pedido.getCliente().getCpf(),
          pedido.getCliente().getNome(),
          pedido.getEnderecoEntrega(),
          LocalDateTime.now()
      );

      return new PagarPedidoResponse(
          true,
          "Pagamento confirmado. Pedido " + idPedido + " encaminhado para a cozinha.",
          idPedido,
          Pedido.Status.PAGO.name());

    } catch (IllegalArgumentException e) {
      // Erros de validação
      return new PagarPedidoResponse(
          false,
          "Erro de validação: " + e.getMessage(),
          idPedido,
          null);
    } catch (RuntimeException e) {
      // Outros erros inesperados
      return new PagarPedidoResponse(
          false,
          "Erro ao processar pagamento: " + e.getMessage(),
          idPedido,
          null);
    }
  }

  // Retorna mensagem de rejeição específica para cada status que impede o
  // pagamento
  private String construirMotivoRejeicao(Pedido.Status statusAtual) {
    return switch (statusAtual) {
      case NOVO ->
        "Pedido ainda não foi aprovado (status: NOVO). Aprove o pedido antes de pagar.";
      case PAGO ->
        "Pedido já foi pago. Não é possível pagar novamente.";
      case CANCELADO ->
        "Pedido foi cancelado e não pode ser pago.";
      case AGUARDANDO, PREPARACAO, PRONTO, TRANSPORTE, ENTREGUE ->
        "Pedido já está em processo de entrega (status: " + statusAtual + "). Não pode ser pago.";
      default ->
        "Pedido não pode ser pago. Status atual: " + statusAtual + ".";
    };
  }
}
