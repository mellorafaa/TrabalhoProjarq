package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
import java.util.List;

//Representa a receita de um produto, contendo título e lista de ingredientes
public class Receita {

  private final long id;
  private final String titulo;
  private final List<Ingrediente> ingredientes;

  //Cria uma receita com id, título e lista de ingredientes
  public Receita(long id, String titulo, List<Ingrediente> ingredientes) {
    this.id = id;
    this.titulo = titulo;
    this.ingredientes = ingredientes;
  }

  //Retorna o ID da receita
  public long getId()             { return id; }

  //Retorna o título da receita
  public String getTitulo()          { return titulo; }

  //Retorna a lista de ingredientes da receita
  public List<Ingrediente> getIngredientes()  { return ingredientes; }
}
