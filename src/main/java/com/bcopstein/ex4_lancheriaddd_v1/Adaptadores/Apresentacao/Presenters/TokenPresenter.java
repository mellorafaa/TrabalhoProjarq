package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;
// Presenter que formata os dados do token JWT e do usuário autenticado para a resposta HTTP

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.TokenResponse;

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

  public String getAccessToken() {
    return accessToken;
  }

  public String getTipo() {
    return tipo;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public String getUsuarioId() {
    return usuarioId;
  }

  public String getUsuarioEmail() {
    return usuarioEmail;
  }
}
