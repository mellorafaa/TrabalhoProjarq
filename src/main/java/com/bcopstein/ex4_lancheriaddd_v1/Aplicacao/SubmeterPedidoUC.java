package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que valida entrada, verifica estoque, calcula valores e submete um pedido ao sistema

// Orquestra:
//   1. Validação de entrada (itens, endereço)
//   2. Validação de cliente (existe, está ativo)
//   3. Validação de itens e conversão para domínio
//   4. Verificação de estoque
//   5. Cálculo de valores (subtotal, desconto, impostos, valorCobrado)
//   6. Criação e aprovação do pedido

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.ItemPedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.SolicitacaoItem;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoCalculador;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoCalculador.ValorPedidoDto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoValidador;

@Component
public class SubmeterPedidoUC {

  private final PedidoValidador pedidoValidador;
  private final PedidoCalculador pedidoCalculador;
  private final PedidoService pedidoService;

  public SubmeterPedidoUC(
      PedidoValidador pedidoValidador,
      PedidoCalculador pedidoCalculador,
      PedidoService pedidoService) {
    this.pedidoValidador = pedidoValidador;
    this.pedidoCalculador = pedidoCalculador;
    this.pedidoService = pedidoService;
  }

  // Executa o caso de uso de submissão de pedido
  // Retorna PedidoResponse com sucesso/erro e os dados do pedido ou itens
  // indisponíveis
  public PedidoResponse run(String clienteCpf, String enderecoEntrega,
      List<ItemPedidoRequest> itensSolicitados) {

    if (!validarDadosEntrada(itensSolicitados)) {
      return new PedidoResponse(null, false,
          "O pedido deve conter pelo menos um item", List.of());
    }

    if (enderecoEntrega == null || enderecoEntrega.isBlank()) {
      return new PedidoResponse(null, false,
          "Endereço de entrega é obrigatório", List.of());
    }

    String erroValidacao = validarItens(itensSolicitados);
    if (erroValidacao != null) {
      return new PedidoResponse(null, false, erroValidacao, List.of());
    }

    try {
      List<SolicitacaoItem> solicitacoes = converterParaSolicitacoes(itensSolicitados);

      // Valida cliente e converte itens para objetos de domínio
      Cliente cliente = pedidoValidador.validarCliente(clienteCpf);
      List<ItemPedido> itens = pedidoValidador.validarEConverterItens(solicitacoes);

      // Verifica disponibilidade em estoque
      List<ItemPedido> itensIndisponiveis = pedidoValidador.verificarEstoque(itens);
      if (!itensIndisponiveis.isEmpty()) {
        return new PedidoResponse(null, false,
            "Pedido negado por falta de ingredientes", itensIndisponiveis);
      }

      // Calcula todos os valores: subtotal, desconto (fidelidade), impostos, valor
      // cobrado
      ValorPedidoDto valores = pedidoCalculador.calcularValorCompleto(itens, clienteCpf);

      // Cria e aprova o pedido persistindo no banco de dados
      Pedido pedidoAprovado = pedidoService.criarEAprovarPedido(
          cliente, enderecoEntrega, itens, valores);

      return new PedidoResponse(pedidoAprovado, true,
          "Pedido aprovado com sucesso! Número: " + pedidoAprovado.getId(), List.of());

    } catch (IllegalArgumentException e) {
      // Erros de validação de negócio
      return new PedidoResponse(null, false, e.getMessage(), List.of());
    } catch (RuntimeException e) {
      // Outros erros inesperados
      return new PedidoResponse(null, false,
          "Erro ao processar pedido: " + e.getMessage(), List.of());
    }
  }

  // Valida se a lista de itens não está vazia
  private boolean validarDadosEntrada(List<ItemPedidoRequest> itensSolicitados) {
    return itensSolicitados != null && !itensSolicitados.isEmpty();
  }

  // Valida que cada item tem quantidade maior que zero
  // Retorna mensagem de erro ou null se válido
  private String validarItens(List<ItemPedidoRequest> itensSolicitados) {
    for (ItemPedidoRequest itemRequest : itensSolicitados) {
      if (itemRequest.getProdutoId() <= 0) {
        return "ID do produto inválido: " + itemRequest.getProdutoId() + ". Deve ser maior que zero";
      }

      if (itemRequest.getQuantidade() <= 0) {
        return "Quantidade inválida para o produto ID " + itemRequest.getProdutoId()
            + ": deve ser maior que zero";
      }
    }
    return null;
  }

  // Converte os ItemPedidoRequest da API em SolicitacaoItem do domínio
  private List<SolicitacaoItem> converterParaSolicitacoes(List<ItemPedidoRequest> itensSolicitados) {
    List<SolicitacaoItem> solicitacoes = new ArrayList<>();
    for (ItemPedidoRequest itemRequest : itensSolicitados) {
      solicitacoes.add(new SolicitacaoItem(itemRequest.getProdutoId(), itemRequest.getQuantidade()));
    }
    return solicitacoes;
  }
}
