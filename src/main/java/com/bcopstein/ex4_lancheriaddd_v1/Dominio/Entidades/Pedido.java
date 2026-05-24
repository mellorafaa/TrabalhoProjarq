package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
// Classe Pedido: responsabilidade principal inferida pelo nome 

import java.time.LocalDateTime;
import java.util.List;

//Entidade de domínio que representa um pedido feito por um cliente na lancheria
public class Pedido {

  //Enum com os possíveis estados do ciclo de vida de um pedido
  public enum Status {
    NOVO, APROVADO, PAGO, AGUARDANDO,
    PREPARACAO, PRONTO, TRANSPORTE, ENTREGUE, CANCELADO
  }

  private final long id;
  private final Cliente cliente;
  private final String enderecoEntrega;
  private final LocalDateTime dataHoraPagamento;
  private final List<ItemPedido> itens;
  private Status status;
  private double valor;
  private double impostos;
  private double desconto;
  private double valorCobrado;

  //Cria um pedido com todos os seus dados, incluindo status e valores calculados
  public Pedido(long id, Cliente cliente, String enderecoEntrega, LocalDateTime dataHoraPagamento,
         List<ItemPedido> itens, Status status, double valor, double impostos,
         double desconto, double valorCobrado) {
    this.id = id;
    this.cliente = cliente;
    this.enderecoEntrega = enderecoEntrega;
    this.dataHoraPagamento = dataHoraPagamento;
    this.itens = itens;
    this.status = status;
    this.valor = valor;
    this.impostos = impostos;
    this.desconto = desconto;
    this.valorCobrado = valorCobrado;
  }

  //Aprova o pedido definindo os valores financeiros calculados
  public void aprovar(double valor, double impostos, double desconto, double valorCobrado) {
    this.status = Status.APROVADO;
    this.valor = valor;
    this.impostos = impostos;
    this.desconto = desconto;
    this.valorCobrado = valorCobrado;
  }

  //Marca o pedido como pago
  public void pagar()      { this.status = Status.PAGO; }

  //Marca o pedido como cancelado
  public void cancelar()     { this.status = Status.CANCELADO; }

  //Coloca o pedido em espera na fila da cozinha
  public void iniciarPreparo()  { this.status = Status.AGUARDANDO; }

  //Inicia a preparação do pedido na cozinha
  public void comecarPreparacao(){ this.status = Status.PREPARACAO; }

  //Marca o pedido como pronto para entrega
  public void marcarPronto()   { this.status = Status.PRONTO; }

  //Inicia o transporte do pedido para o cliente
  public void iniciarTransporte(){ this.status = Status.TRANSPORTE; }

  //Marca o pedido como entregue ao cliente
  public void marcarEntregue()  { this.status = Status.ENTREGUE; }

  //Retorna o ID do pedido
  public long getId()             { return id; }

  //Retorna o cliente que fez o pedido
  public Cliente getCliente()         { return cliente; }

  //Retorna o endereço de entrega do pedido
  public String getEnderecoEntrega()     { return enderecoEntrega; }

  //Retorna a data e hora em que o pagamento foi registrado
  public LocalDateTime getDataHoraPagamento() { return dataHoraPagamento; }

  //Retorna a lista de itens do pedido
  public List<ItemPedido> getItens()     { return itens; }

  //Retorna o status atual do pedido
  public Status getStatus()          { return status; }

  //Retorna o valor bruto total dos itens do pedido
  public double getValor()          { return valor; }

  //Retorna o valor dos impostos do pedido
  public double getImpostos()         { return impostos; }

  //Retorna o valor do desconto aplicado ao pedido
  public double getDesconto()         { return desconto; }

  //Retorna o valor final cobrado ao cliente (valor - desconto + impostos)
  public double getValorCobrado()       { return valorCobrado; }
}
