package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que autentica o usuário pelo e-mail e senha e retorna um token JWT de acesso

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.LoginRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.TokenResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Usuario;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.AutenticacaoServico;

@Component
public class FazerLoginUC {
  
  private final AutenticacaoServico autenticacaoServico;
  private final IGeradorToken geradorToken;
  
  public FazerLoginUC(AutenticacaoServico autenticacaoServico, IGeradorToken geradorToken) {
    this.autenticacaoServico = autenticacaoServico;
    this.geradorToken = geradorToken;
  }
  
  
  // Autentica o usuário e monta a resposta com o token JWT, tipo e dados do usuário
  public TokenResponse executar(LoginRequest request) {
    if (request == null || request.getEmail() == null || request.getSenha() == null) {
      throw new IllegalArgumentException("Email e senha são obrigatórios");
    }

    Usuario usuario = autenticacaoServico.autenticar(request.getEmail(), request.getSenha());

    String token = geradorToken.gerarToken(usuario);
    long expiracaoMs = geradorToken.getTempoExpiracaoMs();

    return new TokenResponse(
      token,
      "Bearer",
      expiracaoMs,
      usuario.getId(),
      usuario.getEmail()
    );
  }
}
