package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe PedidoCalculador: responsabilidade principal inferida pelo nome 

import java.util.List;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;

//Serviço de domínio responsável pelos cálculos financeiros de um pedido (subtotal, imposto, desconto)
@Service
public class PedidoCalculador {

  private final IImpostoService impostoService;
  private final IDescontoService descontoService;

  //Injeta os serviços de imposto e desconto utilizados nos cálculos
  public PedidoCalculador(IImpostoService impostoService, IDescontoService descontoService) {
    this.impostoService = impostoService;
    this.descontoService = descontoService;
  }

  //Calcula o subtotal somando (preço * quantidade) de cada item do pedido
  public double calcularSubtotal(List<ItemPedido> itens) {
    return itens.stream()
        .mapToDouble(item -> (double) item.getItem().getPreco() * item.getQuantidade())
        .sum();
  }

  //Calcula o desconto aplicável ao cliente com base no histórico de pedidos
  public double calcularDesconto(double subtotal, String clienteCpf) {
    return descontoService.calcularDesconto(subtotal, clienteCpf);
  }

  //Calcula o valor dos impostos sobre o subtotal do pedido
  public double calcularImpostos(double subtotal) {
    return impostoService.calcularImposto(subtotal);
  }

  //Calcula o valor final cobrado: (subtotal - desconto) + impostos
  public double calcularValorCobrado(double subtotal, double desconto, double impostos) {
    return (subtotal - desconto) + impostos;
  }

  //Executa todos os cálculos de uma vez e retorna um DTO com os valores do pedido
  public ValorPedidoDto calcularValorCompleto(List<ItemPedido> itens, String clienteCpf) {
    double subtotal = calcularSubtotal(itens);
    double desconto = calcularDesconto(subtotal, clienteCpf);
    double impostos = calcularImpostos(subtotal);
    double valorCobrado = calcularValorCobrado(subtotal, desconto, impostos);

    return new ValorPedidoDto(subtotal, desconto, impostos, valorCobrado);
  }

  //DTO que encapsula os valores financeiros calculados de um pedido
  public static class ValorPedidoDto {
    public final double subtotal;
    public final double desconto;
    public final double impostos;
    public final double valorCobrado;

    //Cria o DTO com todos os valores calculados do pedido
    public ValorPedidoDto(double subtotal, double desconto, double impostos, double valorCobrado) {
      this.subtotal = subtotal;
      this.desconto = desconto;
      this.impostos = impostos;
      this.valorCobrado = valorCobrado;
    }
  }
}
