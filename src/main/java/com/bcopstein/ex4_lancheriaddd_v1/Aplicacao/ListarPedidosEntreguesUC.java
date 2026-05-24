package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Classe ListarPedidosEntreguesUC: responsabilidade principal inferida pelo nome 

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;

@Component
public class ListarPedidosEntreguesUC {

  private final PedidoRepository pedidoRepository;

  public ListarPedidosEntreguesUC(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  // Método run: public run — descrição breve 
  public List<PedidoResponse> run(LocalDate inicio, LocalDate fim) {
    return pedidoRepository.listarEntreguesEntreDatas(inicio, fim).stream()
        .map(p -> new PedidoResponse(p, true, "OK", List.of()))
        .toList();
  }
}
