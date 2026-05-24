package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
//Representa um item dentro de um pedido, associando produto e quantidade solicitada
public class ItemPedido {

  private final Produto item;
  private final int quantidade;

  //Cria um item de pedido com o produto e a quantidade solicitada
  public ItemPedido(Produto item, int quantidade) {
    this.item = item;
    this.quantidade = quantidade;
  }

  //Retorna o produto associado a este item
  public Produto getItem()   { return item; }

  //Retorna a quantidade do produto neste item
  public int getQuantidade()  { return quantidade; }
}
