package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests;
// Classe LoginRequest: responsabilidade principal inferida pelo nome 

/**
 * Request para o caso de uso de login
 * Contém as credenciais fornecidas pelo usuário
 */
public class LoginRequest {
  private String email;
  private String senha;

  public LoginRequest() {
  }

  public LoginRequest(String email, String senha) {
    this.email = email;
    this.senha = senha;
  }

  // Método getEmail: public getEmail — descrição breve 
  public String getEmail() {
    return email;
  }

  // Método setEmail: public setEmail — descrição breve 
  public void setEmail(String email) {
    this.email = email;
  }

  // Método getSenha: public getSenha — descrição breve 
  public String getSenha() {
    return senha;
  }

  // Método setSenha: public setSenha — descrição breve 
  public void setSenha(String senha) {
    this.senha = senha;
  }
}
