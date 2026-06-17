package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.List;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;

//Serviço de domínio responsável pelos cálculos financeiros de um pedido (subtotal, imposto, desconto)
//Fórmula aplicada: valorCobrado = (subtotal - desconto) + imposto
//Onde:
//  - Subtotal = soma dos preços dos itens
//  - Desconto = desconto de fidelidade (7% se cliente tem 3+ pedidos em 20 dias)
//  - Imposto = 10% sobre o subtotal bruto (SEMPRE sobre valor sem desconto)
@Service
public class PedidoCalculador {

  private final IImpostoService impostoService;
  private final IDescontoService descontoService;

  // Injeta os serviços de imposto e desconto utilizados nos cálculos
  public PedidoCalculador(IImpostoService impostoService, IDescontoService descontoService) {
    this.impostoService = impostoService;
    this.descontoService = descontoService;
  }

  // Calcula o subtotal somando (preço * quantidade) de cada item do pedido
  public double calcularSubtotal(List<ItemPedido> itens) {
    if (itens == null || itens.isEmpty()) {
      throw new IllegalArgumentException("Pedido deve conter ao menos um item");
    }

    double subtotal = itens.stream()
        .mapToDouble(item -> (double) item.getItem().getPreco() * item.getQuantidade())
        .sum();

    if (subtotal < 0) {
      throw new IllegalArgumentException("Subtotal calculado é negativo: " + subtotal);
    }

    return subtotal;
  }

  // Calcula o desconto aplicável ao cliente com base no histórico de pedidos
  // Desconto é aplicado sobre o subtotal bruto
  public double calcularDesconto(double subtotal, String clienteCpf) {
    if (subtotal < 0) {
      throw new IllegalArgumentException("Subtotal não pode ser negativo: " + subtotal);
    }

    return descontoService.calcularDesconto(subtotal, clienteCpf);
  }

  // Calcula o valor dos impostos sobre o subtotal bruto do pedido (ANTES do
  // desconto)
  // Os impostos não são reduzidos pelo desconto de fidelidade
  public double calcularImpostos(double subtotal) {
    if (subtotal < 0) {
      throw new IllegalArgumentException("Subtotal não pode ser negativo: " + subtotal);
    }

    return impostoService.calcularImposto(subtotal);
  }

  // Calcula o valor final cobrado: (subtotal - desconto) + impostos
  // Ordem de aplicação: desconto é subtraído primeiro, depois imposto é
  // adicionado
  public double calcularValorCobrado(double subtotal, double desconto, double impostos) {
    if (subtotal < 0 || desconto < 0 || impostos < 0) {
      throw new IllegalArgumentException(
          "Valores não podem ser negativos - Subtotal: " + subtotal +
              ", Desconto: " + desconto + ", Impostos: " + impostos);
    }

    if (desconto > subtotal) {
      throw new IllegalArgumentException(
          "Desconto não pode ser maior que o subtotal");
    }

    return (subtotal - desconto) + impostos;
  }

  // Executa todos os cálculos de uma vez e retorna um DTO com os valores do
  // pedido
  // Validações: subtotal > 0, desconto >= 0, impostos >= 0, valorCobrado > 0
  public ValorPedidoDto calcularValorCompleto(List<ItemPedido> itens, String clienteCpf) {
    double subtotal = calcularSubtotal(itens);
    double desconto = calcularDesconto(subtotal, clienteCpf);
    double impostos = calcularImpostos(subtotal);
    double valorCobrado = calcularValorCobrado(subtotal, desconto, impostos);

    if (valorCobrado <= 0) {
      throw new IllegalArgumentException(
          "Valor cobrado deve ser maior que zero: " + valorCobrado);
    }

    return new ValorPedidoDto(subtotal, desconto, impostos, valorCobrado);
  }

  // DTO que encapsula os valores financeiros calculados de um pedido
  public static class ValorPedidoDto {
    public final double subtotal; // Soma dos preços dos itens
    public final double desconto; // Desconto de fidelidade (7%)
    public final double impostos; // Impostos (10% sobre subtotal)
    public final double valorCobrado; // Valor final: (subtotal - desconto) + impostos

    // Cria o DTO com todos os valores calculados do pedido
    public ValorPedidoDto(double subtotal, double desconto, double impostos, double valorCobrado) {
      this.subtotal = subtotal;
      this.desconto = desconto;
      this.impostos = impostos;
      this.valorCobrado = valorCobrado;
    }
  }
}
