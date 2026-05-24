package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
//Interface de serviço de criptografia de senha; define o contrato de hash e verificação de senhas
public interface CriptografiaSenhaServico {

  //Gera o hash criptográfico de uma senha em texto plano
  String criptografar(String senhaTextoPlano);

  //Verifica se a senha em texto plano corresponde ao hash armazenado
  boolean verificar(String senhaTextoPlano, String senhaHash);
}
