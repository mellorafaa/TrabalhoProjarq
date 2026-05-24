package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;
// Presenter com o ID e o status atual de um pedido para a resposta HTTP

public class PedidoStatusPresenter {
  private final long id;
  private final String status;

  public PedidoStatusPresenter(long id, String status) {
    this.id = id;
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }
}
