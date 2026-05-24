package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
// Classe ItemEstoque: responsabilidade principal inferida pelo nome 

//Representa um item do estoque, associando um ingrediente à sua quantidade disponível
public class ItemEstoque {
  private Ingrediente ingrediente;
  private int quantidade;

  //Cria um item de estoque com ingrediente e quantidade disponível
  public ItemEstoque(Ingrediente ingrediente, int quantidade) {
    this.ingrediente = ingrediente;
    this.quantidade = quantidade;
  }

  //Retorna o ingrediente associado a este item de estoque
  public Ingrediente getIngrediente() { return ingrediente; }

  //Retorna a quantidade disponível em estoque
  public int getQuantidade() { return quantidade; }

  //Atualiza a quantidade disponível em estoque
  public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
