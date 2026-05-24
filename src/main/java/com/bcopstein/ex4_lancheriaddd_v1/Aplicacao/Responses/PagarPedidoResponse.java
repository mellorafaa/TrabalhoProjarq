package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
//Resposta do caso de uso de pagamento de pedido, com status, mensagem e id do pedido
public class PagarPedidoResponse {

  private final boolean pago;
  private final String mensagem;
  private final long idPedido;
  private final String status;

  //Cria a resposta com resultado do pagamento, mensagem, id e status do pedido
  public PagarPedidoResponse(boolean pago, String mensagem, long idPedido, String status) {
    this.pago   = pago;
    this.mensagem = mensagem;
    this.idPedido = idPedido;
    this.status  = status;
  }

  //Informa se o pagamento foi processado com sucesso
  public boolean isPago()    { return pago; }

  //Retorna a mensagem descritiva do resultado do pagamento
  public String getMensagem()  { return mensagem; }

  //Retorna o ID do pedido pago
  public long getIdPedido()   { return idPedido; }

  //Retorna o status atual do pedido após o pagamento
  public String getStatus()   { return status; }
}
