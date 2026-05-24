package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;
// Implementação fake do serviço de pagamento usada no perfil dev; aprova todo pagamento automaticamente

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IPagamentoService;

@Service
@Profile("dev")
public class PagamentoServiceFake implements IPagamentoService {

  @Override
  // Simula o processamento de pagamento aprovando sempre (retorna true)
  public boolean processarPagamento(Pedido pedido) {
    return true;
  }
}
