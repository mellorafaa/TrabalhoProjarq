package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
// Classe Ingrediente: responsabilidade principal inferida pelo nome 

//Representa um ingrediente utilizado nas receitas dos produtos da lancheria
public class Ingrediente {

  private final long id;
  private final String descricao;

  //Cria um ingrediente com id e descrição
  public Ingrediente(long id, String descricao) {
    this.id = id;
    this.descricao = descricao;
  }

  //Retorna o ID do ingrediente
  public long getId()     { return id; }

  //Retorna a descrição do ingrediente
  public String getDescricao() { return descricao; }
}
