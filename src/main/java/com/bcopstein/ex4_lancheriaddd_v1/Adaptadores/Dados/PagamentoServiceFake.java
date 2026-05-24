package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;
// Classe PagamentoServiceFake: responsabilidade principal inferida pelo nome 

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IPagamentoService;

@Service
@Profile("dev")
public class PagamentoServiceFake implements IPagamentoService {

  @Override
  // Método processarPagamento: public processarPagamento — descrição breve 
  public boolean processarPagamento(Pedido pedido) {
    return true;
  }
}
