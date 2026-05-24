package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
//Entidade de domínio que representa um usuário do sistema para fins de autenticação
public class Usuario {

  private final String id;
  private final String email;
  private final String senhaHash;
  private final String nome;
  private final String role;
  private final boolean ativo;

  //Cria um usuário completo com todos os atributos, incluindo id e status ativo
  public Usuario(String id, String email, String senhaHash, String nome, String role, boolean ativo) {
    this.id = id;
    this.email = email;
    this.senhaHash = senhaHash;
    this.nome = nome;
    this.role = role;
    this.ativo = ativo;
  }

  //Cria um novo usuário sem id (gerado pelo repositório) e ativo por padrão
  public Usuario(String email, String senhaHash, String nome, String role) {
    this(null, email, senhaHash, nome, role, true);
  }

  //Retorna o ID único do usuário
  public String getId()    { return id; }

  //Retorna o e-mail do usuário
  public String getEmail()   { return email; }

  //Retorna o hash da senha do usuário
  public String getSenhaHash() { return senhaHash; }

  //Retorna o nome do usuário
  public String getNome()   { return nome; }

  //Retorna o papel (role) do usuário no sistema (ex: "USER", "ADMIN")
  public String getRole()   { return role; }

  //Verifica se o usuário está ativo
  public boolean isAtivo()   { return ativo; }

  //Alias de isAtivo() para verificar se o usuário está ativo
  public boolean estaAtivo()  { return ativo; }
}
