package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe UsuarioService: responsabilidade principal inferida pelo nome 

import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.UsuarioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Usuario;

//Serviço de domínio responsável pelas operações de persistência de usuários do sistema
@Service
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  //Injeta o repositório de usuários
  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  //Persiste um usuário e retorna o objeto salvo com ID gerado
  public Usuario salvar(Usuario usuario) {
    return usuarioRepository.salvar(usuario);
  }
}
