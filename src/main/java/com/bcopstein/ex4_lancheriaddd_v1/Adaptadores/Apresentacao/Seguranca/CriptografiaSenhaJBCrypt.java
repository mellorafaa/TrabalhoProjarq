package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Seguranca;
// Implementação de criptografia de senhas usando BCrypt para geração de hash e verificação

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CriptografiaSenhaServico;

@Component
public class CriptografiaSenhaJBCrypt implements CriptografiaSenhaServico {

  private final BCryptPasswordEncoder encoder;

  public CriptografiaSenhaJBCrypt() {
    this.encoder = new BCryptPasswordEncoder();
  }

  @Override
  // Gera o hash BCrypt da senha em texto plano; lança exceção se a senha for nula ou vazia
  public String criptografar(String senhaTextoPlano) {
    if (senhaTextoPlano == null || senhaTextoPlano.isBlank()) {
      throw new IllegalArgumentException("Senha não pode estar vazia");
    }
    return encoder.encode(senhaTextoPlano);
  }

  @Override
  // Verifica se a senha em texto plano corresponde ao hash BCrypt armazenado
  public boolean verificar(String senhaTextoPlano, String senhaHash) {
    if (senhaTextoPlano == null || senhaHash == null) {
      return false;
    }
    return encoder.matches(senhaTextoPlano, senhaHash);
  }
}
