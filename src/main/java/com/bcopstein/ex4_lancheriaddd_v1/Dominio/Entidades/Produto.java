package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
//Entidade de domínio que representa um produto disponível no cardápio da lancheria
public class Produto {

  private final long id;
  private final String descricao;
  private final Receita receita;
  private int preco;

  //Cria um produto validando preço, descrição e receita; lança exceção se inválidos
  public Produto(long id, String descricao, Receita receita, int preco) {
    if (!Produto.precoValido(preco))
      throw new IllegalArgumentException("Preco invalido: " + preco);
    if (descricao == null || descricao.isBlank())
      throw new IllegalArgumentException("Descricao invalida");
    if (receita == null)
      throw new IllegalArgumentException("Receita invalida");
    this.id = id;
    this.descricao = descricao;
    this.receita = receita;
    this.preco = preco;
  }

  //Retorna o ID do produto
  public long getId()     { return id; }

  //Retorna a descrição do produto
  public String getDescricao() { return descricao; }

  //Retorna a receita associada ao produto
  public Receita getReceita() { return receita; }

  //Retorna o preço do produto em centavos
  public int getPreco()    { return preco; }

  //Atualiza o preço do produto, validando que seja maior que zero
  public void setPreco(int preco) {
    if (!Produto.precoValido(preco))
      throw new IllegalArgumentException("Preco invalido: " + preco);
    this.preco = preco;
  }

  //Verifica se um preço é válido (maior que zero)
  public static boolean precoValido(int preco) {
    return preco > 0;
  }

  //Representação textual do produto para depuração
  @Override
  public String toString() {
    return "Produto [id=" + id + ", descricao=" + descricao + ", receita=" + receita + ", preco=" + preco + "]";
  }
}
