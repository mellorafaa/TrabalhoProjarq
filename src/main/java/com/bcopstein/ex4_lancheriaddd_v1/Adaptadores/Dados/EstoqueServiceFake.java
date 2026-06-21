package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;
// Implementação fake do serviço de estoque usada no perfil dev; aprova todos os itens sem verificação real

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;

@Service
@Profile("fake")
public class EstoqueServiceFake implements IEstoqueService {

  @Override
  // Simula verificação aprovando todos os itens (retorna lista vazia de indisponíveis)
  public List<ItemPedido> verificarEstoque(List<ItemPedido> itens) {
    return List.of();
  }
}
