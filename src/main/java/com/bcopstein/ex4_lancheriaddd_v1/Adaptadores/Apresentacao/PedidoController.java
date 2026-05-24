package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;
// Controller REST que gerencia o ciclo de vida dos pedidos via endpoints em /pedidos

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoStatusPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CancelarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarPedidosClienteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarPedidosClienteEntreguesUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarPedidosEntreguesUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarPedidosUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.PagarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.PedidoSubmissaoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CancelarPedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PagarPedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoStatusResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SolicitarStatusPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmeterPedidoUC;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  private final SubmeterPedidoUC submeterPedidoUC;
  private final ListarPedidosUC listarPedidosUC;
  private final SolicitarStatusPedidoUC solicitarStatusPedidoUC;
  private final CancelarPedidoUC cancelarPedidoUC;
  private final PagarPedidoUC pagarPedidoUC;
  private final ListarPedidosEntreguesUC listarPedidosEntreguesUC;
  private final ListarPedidosClienteEntreguesUC listarPedidosClienteEntreguesUC;
  private final ListarPedidosClienteUC listarPedidosClienteUC;

  public PedidoController(SubmeterPedidoUC submeterPedidoUC,
              ListarPedidosUC listarPedidosUC,
              SolicitarStatusPedidoUC solicitarStatusPedidoUC,
              CancelarPedidoUC cancelarPedidoUC,
              PagarPedidoUC pagarPedidoUC,
              ListarPedidosEntreguesUC listarPedidosEntreguesUC,
              ListarPedidosClienteEntreguesUC listarPedidosClienteEntreguesUC,
              ListarPedidosClienteUC listarPedidosClienteUC) {
    this.submeterPedidoUC = submeterPedidoUC;
    this.listarPedidosUC = listarPedidosUC;
    this.solicitarStatusPedidoUC = solicitarStatusPedidoUC;
    this.cancelarPedidoUC = cancelarPedidoUC;
    this.pagarPedidoUC = pagarPedidoUC;
    this.listarPedidosEntreguesUC = listarPedidosEntreguesUC;
    this.listarPedidosClienteEntreguesUC = listarPedidosClienteEntreguesUC;
    this.listarPedidosClienteUC = listarPedidosClienteUC;
  }

  @GetMapping("/cliente/{cpf}/entregues")
  @CrossOrigin("*")
  public ResponseEntity<List<PedidoPresenter>> listarPedidosClienteEntregues(
      @PathVariable String cpf,
      @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
      @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
    List<PedidoPresenter> presenters = listarPedidosClienteEntreguesUC.run(cpf, inicio, fim)
        .stream().map(this::montarPresenter).toList();
    return ResponseEntity.ok(presenters);
  }

  @GetMapping("/cliente/{cpf}")
  @CrossOrigin("*")
  // Retorna todos os pedidos de um cliente específico pelo CPF
  public ResponseEntity<List<PedidoPresenter>> listarPedidosCliente(@PathVariable String cpf) {
    List<PedidoPresenter> presenters = listarPedidosClienteUC.run(cpf)
        .stream().map(this::montarPresenter).toList();
    return ResponseEntity.ok(presenters);
  }

  @GetMapping("/entregues")
  @CrossOrigin("*")
  public ResponseEntity<List<PedidoPresenter>> listarPedidosEntregues(
      @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
      @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
    List<PedidoPresenter> presenters = listarPedidosEntreguesUC.run(inicio, fim)
        .stream().map(this::montarPresenter).toList();
    return ResponseEntity.ok(presenters);
  }

  @GetMapping
  @CrossOrigin("*")
  // Retorna a lista de todos os pedidos cadastrados no sistema
  public ResponseEntity<List<PedidoPresenter>> listarPedidos() {
    List<PedidoPresenter> presenters = listarPedidosUC.run()
        .stream().map(this::montarPresenter).toList();
    return ResponseEntity.ok(presenters);
  }

  @GetMapping("/{id}")
  @CrossOrigin("*")
  // Retorna o ID e o status atual de um pedido; responde 404 se o pedido não for encontrado
  public ResponseEntity<PedidoStatusPresenter> recuperarStatusPedido(@PathVariable long id) {
    PedidoStatusResponse statusResponse = solicitarStatusPedidoUC.run(id);
    if (statusResponse == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(new PedidoStatusPresenter(statusResponse.id(), statusResponse.status()));
  }

  @PostMapping
  @CrossOrigin("*")
  // Valida e submete um novo pedido; retorna 422 se negado por falta de estoque ou dados inválidos
  public ResponseEntity<PedidoPresenter> submeterPedido(@RequestBody PedidoSubmissaoRequest request) {
    PedidoResponse response = submeterPedidoUC.run(
        request.getClienteCpf(),
        request.getEnderecoEntrega(),
        request.getItens());
    PedidoPresenter presenter = montarPresenter(response);
    if (response.isAprovado()) {
      return ResponseEntity.ok(presenter);
    }
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(presenter);
  }

  @PostMapping("/{id}/cancelar")
  @CrossOrigin("*")
  // Cancela um pedido aprovado; retorna 422 se o status atual não permitir cancelamento
  public ResponseEntity<CancelarPedidoResponse> cancelarPedido(@PathVariable long id) {
    CancelarPedidoResponse response = cancelarPedidoUC.run(id);
    if (response.isCancelado()) {
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
  }

  @PostMapping("/{id}/pagar")
  @CrossOrigin("*")
  // Processa o pagamento de um pedido aprovado e encaminha à cozinha; retorna 422 se não for possível
  public ResponseEntity<PagarPedidoResponse> pagarPedido(@PathVariable long id) {
    PagarPedidoResponse response = pagarPedidoUC.run(id);
    if (response.isPago()) {
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
  }

  // Monta o PedidoPresenter a partir do response, tratando pedidos negados e itens indisponíveis
  private PedidoPresenter montarPresenter(PedidoResponse response) {
    List<PedidoPresenter.ItemPedidoPresenter> indisponiveis =
        response.getItensIndisponiveis().stream()
            .map(item -> new PedidoPresenter.ItemPedidoPresenter(
                item.produtoId(), item.descricao(), item.precoUnitario(), item.quantidade()))
            .collect(Collectors.toList());

    if (!response.isAprovado() || response.getPedido() == null) {
      return new PedidoPresenter(
          0, "NEGADO", 0, 0, 0, 0,
          false, response.getMensagem(), "",
          List.of(), indisponiveis);
    }

    PedidoResponse.PedidoDTO dto = response.getPedido();
    List<PedidoPresenter.ItemPedidoPresenter> itensPresenter = dto.itens().stream()
        .map(item -> new PedidoPresenter.ItemPedidoPresenter(
            item.produtoId(), item.descricao(), item.precoUnitario(), item.quantidade()))
        .collect(Collectors.toList());

    return new PedidoPresenter(
        dto.id(), dto.status(), dto.valor(), dto.desconto(),
        dto.impostos(), dto.valorCobrado(),
        response.isAprovado(), response.getMensagem(),
        dto.enderecoEntrega(), itensPresenter, List.of());
  }
}
