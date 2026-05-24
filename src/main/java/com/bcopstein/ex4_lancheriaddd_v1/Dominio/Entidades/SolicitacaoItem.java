package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
//Representa a solicitação de um item (produto + quantidade) feita pelo cliente ao submeter pedido
public class SolicitacaoItem {

  private final long produtoId;
  private final int quantidade;

  //Cria uma solicitação de item com id do produto e quantidade desejada
  public SolicitacaoItem(long produtoId, int quantidade) {
    this.produtoId = produtoId;
    this.quantidade = quantidade;
  }

  //Retorna o ID do produto solicitado
  public long getProdutoId() { return produtoId; }

  //Retorna a quantidade solicitada do produto
  public int getQuantidade() { return quantidade; }
}
