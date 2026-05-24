package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Classe IGeradorToken: responsabilidade principal inferida pelo nome 

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Usuario;

//Interface de geração de token de autenticação; define o contrato para criação de tokens JWT
public interface IGeradorToken {
  //Gera um token de autenticação para o usuário informado
  String gerarToken(Usuario usuario);

  //Retorna o tempo de expiração configurado para os tokens em milissegundos
  long getTempoExpiracaoMs();
}
