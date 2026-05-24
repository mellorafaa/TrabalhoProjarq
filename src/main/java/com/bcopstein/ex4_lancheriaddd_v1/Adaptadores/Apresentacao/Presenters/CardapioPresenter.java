package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;
// Classe CardapioPresenter: responsabilidade principal inferida pelo nome 

import java.util.LinkedList;
import java.util.List;

public class CardapioPresenter {
  public class ItemCardapioPresenter{
    private Long id;
    private String descricao;
    private int preco;
    private boolean indicacao;
    
    public ItemCardapioPresenter(Long id, String descricao, int preco, boolean indicacao) {
      this.id = id;
      this.descricao = descricao;
      this.preco = preco;
      this.indicacao = indicacao;
    }

    // Método getId: public getId — descrição breve 
    public Long getId() {
      return id;
    }

    // Método getDescricao: public getDescricao — descrição breve 
    public String getDescricao() {
      return descricao;
    }

    // Método getPreco: public getPreco — descrição breve 
    public int getPreco() {
      return preco;
    }

    // Método isIndicacao: public isIndicacao — descrição breve 
    public boolean isIndicacao() {
      return indicacao;
    }
  }

  private String titulo;
  private List<ItemCardapioPresenter> itens;

  public CardapioPresenter(String titulo){
    this.titulo = titulo;
    itens = new LinkedList<>();
  }

  // Método getTitulo: public getTitulo — descrição breve 
  public String getTitulo(){
    return titulo;
  }

  // Método insereItem: public insereItem — descrição breve 
  public void insereItem(long id,String titulo,int preco,boolean sugestao){
    itens.add(new ItemCardapioPresenter(id, titulo, preco, sugestao));
  }
  
  // Método getItens: public getItens — descrição breve 
  public List<ItemCardapioPresenter> getItens() {
    return itens;
  }

  // Método add: public add — descrição breve 
  public void add(ItemCardapioPresenter item){
    itens.add(item);
  }
}
