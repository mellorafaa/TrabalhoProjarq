package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;
//Entidade de domínio que representa um cliente da lancheria
public class Cliente {

  private final String cpf;
  private final String nome;
  private final String celular;
  private final String endereco;
  private final String email;
  private final String senhaHash;

  //Cria um cliente sem senha (para leitura/consulta)
  public Cliente(String cpf, String nome, String celular, String endereco, String email) {
    this(cpf, nome, celular, endereco, email, null);
  }

  //Cria um cliente completo com hash de senha
  public Cliente(String cpf, String nome, String celular, String endereco, String email, String senhaHash) {
    this.cpf = cpf;
    this.nome = nome;
    this.celular = celular;
    this.endereco = endereco;
    this.email = email;
    this.senhaHash = senhaHash;
  }

  //Retorna o CPF do cliente
  public String getCpf()    { return cpf; }

  //Retorna o nome do cliente
  public String getNome()   { return nome; }

  //Retorna o celular do cliente
  public String getCelular()  { return celular; }

  //Retorna o endereço do cliente
  public String getEndereco() { return endereco; }

  //Retorna o e-mail do cliente
  public String getEmail()   { return email; }

  //Retorna o hash da senha do cliente
  public String getSenhaHash() { return senhaHash; }
}
