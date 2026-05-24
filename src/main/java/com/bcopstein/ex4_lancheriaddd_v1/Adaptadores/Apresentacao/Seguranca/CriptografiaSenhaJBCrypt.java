package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Seguranca;
// Classe CriptografiaSenhaJBCrypt: responsabilidade principal inferida pelo nome 

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
  // Método criptografar: public criptografar — descrição breve 
  public String criptografar(String senhaTextoPlano) {
    if (senhaTextoPlano == null || senhaTextoPlano.isBlank()) {
      throw new IllegalArgumentException("Senha não pode estar vazia");
    }
    return encoder.encode(senhaTextoPlano);
  }

  @Override
  // Método verificar: public verificar — descrição breve 
  public boolean verificar(String senhaTextoPlano, String senhaHash) {
    if (senhaTextoPlano == null || senhaHash == null) {
      return false;
    }
    return encoder.matches(senhaTextoPlano, senhaHash);
  }
}
