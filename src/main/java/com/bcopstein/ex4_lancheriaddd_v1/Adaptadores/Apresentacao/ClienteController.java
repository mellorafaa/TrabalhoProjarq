package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;
// Controller REST que expõe endpoints GET e POST /clientes para listagem e cadastro de clientes

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarClientesUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RegistrarClienteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.RegistrarClienteRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.RegistrarClienteResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.ClientePresenter;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  private final RegistrarClienteUC registrarClienteUC;
  private final ListarClientesUC listarClientesUC;

  public ClienteController(RegistrarClienteUC registrarClienteUC, ListarClientesUC listarClientesUC) {
    this.registrarClienteUC = registrarClienteUC;
    this.listarClientesUC = listarClientesUC;
  }

  @GetMapping
  @CrossOrigin("*")
  // Retorna a lista de todos os clientes cadastrados no sistema
  public List<ClientePresenter> listarClientes() {
    return listarClientesUC.run().stream()
        .map(r -> new ClientePresenter(r.cpf(), r.nome(), r.celular(), r.endereco(), r.email()))
        .toList();
  }

  @PostMapping
  @CrossOrigin("*")
  // Recebe os dados e cadastra um novo cliente, retornando resultado de sucesso ou mensagem de erro
  public RegistrarClienteResponse registrarCliente(@RequestBody RegistrarClienteRequest request) {
    return registrarClienteUC.run(request);
  }
}
