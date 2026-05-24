package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
import java.util.List;

//Representa um cardápio da lancheria, composto por um cabeçalho e lista de produtos
public class Cardapio {

  private final CabecalhoCardapio cabecalhoCardapio;
  private List<Produto> produtos;

  //Cria um cardápio com cabeçalho e lista de produtos
  public Cardapio(CabecalhoCardapio cabecalhoCardapio, List<Produto> produtos) {
    this.cabecalhoCardapio = cabecalhoCardapio;
    this.produtos = produtos;
  }

  //Retorna o cabeçalho do cardápio (id e título)
  public CabecalhoCardapio getCabecalhoCardapio() { return cabecalhoCardapio; }

  //Retorna a lista de produtos do cardápio
  public List<Produto> getProdutos()       { return produtos; }

  //Define a lista de produtos do cardápio
  public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }
}
