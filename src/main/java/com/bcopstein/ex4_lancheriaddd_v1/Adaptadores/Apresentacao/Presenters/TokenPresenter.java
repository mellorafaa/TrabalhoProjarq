package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;
// Classe TokenPresenter: responsabilidade principal inferida pelo nome 

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.TokenResponse;

/**
 * Presenter que converte TokenResponse para formato de apresentação HTTP
 * Responsável pela transformação de dados de domínio para DTO
 */
public class TokenPresenter {

  private String accessToken;
  private String tipo;
  private long expiresIn;
  private String usuarioId;
  private String usuarioEmail;

  public TokenPresenter(TokenResponse response) {
    this.accessToken = response.getToken();
    this.tipo = response.getTipo();
    this.expiresIn = response.getExpiracaoMs();
    this.usuarioId = response.getUsuarioId();
    this.usuarioEmail = response.getUsuarioEmail();
  }

  // Método getAccessToken: public getAccessToken — descrição breve 
  public String getAccessToken() {
    return accessToken;
  }

  // Método getTipo: public getTipo — descrição breve 
  public String getTipo() {
    return tipo;
  }

  // Método getExpiresIn: public getExpiresIn — descrição breve 
  public long getExpiresIn() {
    return expiresIn;
  }

  // Método getUsuarioId: public getUsuarioId — descrição breve 
  public String getUsuarioId() {
    return usuarioId;
  }

  // Método getUsuarioEmail: public getUsuarioEmail — descrição breve 
  public String getUsuarioEmail() {
    return usuarioEmail;
  }
}
