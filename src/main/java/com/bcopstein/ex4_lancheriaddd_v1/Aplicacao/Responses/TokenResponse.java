package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
// Classe TokenResponse: responsabilidade principal inferida pelo nome 

//Resposta do caso de uso de login, contendo o token JWT e informações do usuário autenticado
public class TokenResponse {

  private final String token;
  private final String tipo;
  private final long expiracaoMs;
  private final String usuarioId;
  private final String usuarioEmail;

  //Cria a resposta de token com todos os dados de autenticação
  public TokenResponse(String token, String tipo, long expiracaoMs,
             String usuarioId, String usuarioEmail) {
    this.token = token;
    this.tipo = tipo;
    this.expiracaoMs = expiracaoMs;
    this.usuarioId = usuarioId;
    this.usuarioEmail = usuarioEmail;
  }

  //Retorna o token JWT gerado
  public String getToken()    { return token; }

  //Retorna o tipo do token (ex: "Bearer")
  public String getTipo()     { return tipo; }

  //Retorna o tempo de expiração do token em milissegundos
  public long getExpiracaoMs()  { return expiracaoMs; }

  //Retorna o ID do usuário autenticado
  public String getUsuarioId()  { return usuarioId; }

  //Retorna o e-mail do usuário autenticado
  public String getUsuarioEmail() { return usuarioEmail; }
}
