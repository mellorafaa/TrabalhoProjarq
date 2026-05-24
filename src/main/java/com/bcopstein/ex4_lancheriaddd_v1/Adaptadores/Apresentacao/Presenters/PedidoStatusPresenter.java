package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;
// Classe PedidoStatusPresenter: responsabilidade principal inferida pelo nome 

public class PedidoStatusPresenter {
  private final long id;
  private final String status;

  public PedidoStatusPresenter(long id, String status) {
    this.id = id;
    this.status = status;
  }

  // Método getId: public getId — descrição breve 
  public long getId() {
    return id;
  }

  // Método getStatus: public getStatus — descrição breve 
  public String getStatus() {
    return status;
  }
}
