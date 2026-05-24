package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;
// Classe UsuarioRepository: responsabilidade principal inferida pelo nome 

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Usuario;

//Interface de repositório de usuários; define operações de acesso a dados de usuário do sistema
public interface UsuarioRepository {

  //Busca um usuário pelo e-mail; retorna null se não encontrado
  Usuario recuperarPorEmail(String email);

  //Persiste um novo usuário e retorna o objeto salvo com ID gerado
  Usuario salvar(Usuario usuario);

  //Busca um usuário pelo ID; retorna null se não encontrado
  Usuario recuperarPorId(String id);
}
