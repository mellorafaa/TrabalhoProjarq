package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
// Classe CancelarPedidoResponse: responsabilidade principal inferida pelo nome 

//Resposta do caso de uso de cancelamento de pedido, indicando resultado e id do pedido
public class CancelarPedidoResponse {

  private final boolean cancelado;
  private final String mensagem;
  private final long idPedido;

  //Cria a resposta com status de cancelamento, mensagem e id do pedido
  public CancelarPedidoResponse(boolean cancelado, String mensagem, long idPedido) {
    this.cancelado = cancelado;
    this.mensagem = mensagem;
    this.idPedido = idPedido;
  }

  //Informa se o pedido foi cancelado com sucesso
  public boolean isCancelado() { return cancelado; }

  //Retorna a mensagem descritiva do resultado
  public String getMensagem() { return mensagem; }

  //Retorna o ID do pedido envolvido no cancelamento
  public long getIdPedido()  { return idPedido; }
}
