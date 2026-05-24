package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna todos os clientes cadastrados como lista de respostas

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.ClienteResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ClienteService;

@Component
public class ListarClientesUC {

  private final ClienteService clienteService;

  public ListarClientesUC(ClienteService clienteService) {
    this.clienteService = clienteService;
  }

  // Busca todos os clientes e os converte em ClienteResponse
  public List<ClienteResponse> run() {
    return clienteService.listarClientes().stream()
        .map(c -> new ClienteResponse(
            c.getCpf(), c.getNome(), c.getCelular(), c.getEndereco(), c.getEmail()))
        .toList();
  }
}
