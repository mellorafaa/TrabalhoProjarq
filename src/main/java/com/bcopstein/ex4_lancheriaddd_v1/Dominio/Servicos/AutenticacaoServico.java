package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.UsuarioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Usuario;

//Serviço de domínio responsável pela autenticação de usuários no sistema
@Service
public class AutenticacaoServico {

  private final UsuarioRepository usuarioRepository;
  private final CriptografiaSenhaServico criptografiaSenhaServico;

  //Injeta o repositório de usuários e o serviço de criptografia de senha
  public AutenticacaoServico(UsuarioRepository usuarioRepository,
                CriptografiaSenhaServico criptografiaSenhaServico) {
    this.usuarioRepository = usuarioRepository;
    this.criptografiaSenhaServico = criptografiaSenhaServico;
  }

  //Autentica um usuário pelo e-mail e senha; lança exceção se credenciais inválidas ou usuário inativo
  public Usuario autenticar(String email, String senha) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email não pode estar vazio");
    }

    if (senha == null || senha.isBlank()) {
      throw new IllegalArgumentException("Senha não pode estar vazia");
    }

    Usuario usuario = usuarioRepository.recuperarPorEmail(email);

    if (usuario == null) {
      throw new IllegalArgumentException("Usuário não encontrado");
    }

    if (!usuario.estaAtivo()) {
      throw new IllegalArgumentException("Usuário inativo");
    }

    if (!criptografiaSenhaServico.verificar(senha, usuario.getSenhaHash())) {
      throw new IllegalArgumentException("Senha incorreta");
    }

    return usuario;
  }
}
