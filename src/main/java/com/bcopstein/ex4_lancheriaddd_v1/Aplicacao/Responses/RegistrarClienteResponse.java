package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
// Classe RegistrarClienteResponse: responsabilidade principal inferida pelo nome 

//Resposta do caso de uso de registro de cliente, indicando sucesso ou falha com mensagem
public class RegistrarClienteResponse {
  private final boolean sucesso;
  private final String mensagem;

  //Cria a resposta com indicador de sucesso e mensagem descritiva
  public RegistrarClienteResponse(boolean sucesso, String mensagem) {
    this.sucesso = sucesso;
    this.mensagem = mensagem;
  }

  //Informa se o registro foi concluído com sucesso
  public boolean isSucesso() { return sucesso; }

  //Retorna a mensagem de resultado do registro
  public String getMensagem() { return mensagem; }
}
