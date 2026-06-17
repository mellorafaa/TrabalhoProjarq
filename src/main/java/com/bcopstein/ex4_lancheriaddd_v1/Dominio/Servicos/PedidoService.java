package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoCalculador.ValorPedidoDto;

//Serviço de domínio responsável pela criação e aprovação de pedidos
//Orquestra a validação, cálculo de valores e persistência do pedido
@Service
public class PedidoService {

  private final PedidoRepository pedidoRepository;

  // Injeta o repositório de pedidos
  public PedidoService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  // Cria e aprova um pedido com os valores calculados, persistindo no repositório
  // Parâmetros:
  // - cliente: cliente que fez o pedido
  // - enderecoEntrega: endereço para entrega
  // - itens: lista de itens do pedido
  // - valores: DTO com subtotal, desconto, impostos e valorCobrado já calculados
  public Pedido criarEAprovarPedido(Cliente cliente, String enderecoEntrega,
      List<ItemPedido> itens, ValorPedidoDto valores) {

    if (cliente == null) {
      throw new IllegalArgumentException("Cliente não pode ser nulo");
    }

    if (enderecoEntrega == null || enderecoEntrega.isBlank()) {
      throw new IllegalArgumentException("Endereço de entrega não pode ser vazio");
    }

    if (itens == null || itens.isEmpty()) {
      throw new IllegalArgumentException("Pedido deve conter ao menos um item");
    }

    if (valores == null) {
      throw new IllegalArgumentException("Valores do pedido não podem ser nulos");
    }

    Pedido pedido = new Pedido(
        0, cliente, enderecoEntrega, null,
        itens, Pedido.Status.NOVO,
        0, 0, 0, 0);

    // Aprova o pedido com os valores calculados
    // Subtotal, impostos, desconto e valorCobrado já foram validados em
    // PedidoCalculador
    pedido.aprovar(valores.subtotal, valores.impostos, valores.desconto, valores.valorCobrado);

    return pedidoRepository.salvar(pedido);
  }

  public List<Pedido> listarTodos() {
    return pedidoRepository.listarTodos();
  }

  public List<Pedido> listarPorClienteCpf(String cpf) {
    if (cpf == null || cpf.isBlank()) {
      throw new IllegalArgumentException("CPF não pode ser vazio");
    }
    return pedidoRepository.listarPorClienteCpf(cpf);
  }

  public List<Pedido> listarEntreguesEntreDatas(LocalDate inicio, LocalDate fim) {
    if (inicio == null || fim == null) {
      throw new IllegalArgumentException("Datas de início e fim não podem ser nulas");
    }
    if (inicio.isAfter(fim)) {
      throw new IllegalArgumentException("Data de início não pode ser após data de fim");
    }
    return pedidoRepository.listarEntreguesEntreDatas(inicio, fim);
  }

  public List<Pedido> listarEntreguesEntreDatasParaCliente(String cpf, LocalDate inicio, LocalDate fim) {
    if (cpf == null || cpf.isBlank()) {
      throw new IllegalArgumentException("CPF não pode ser vazio");
    }
    if (inicio == null || fim == null) {
      throw new IllegalArgumentException("Datas de início e fim não podem ser nulas");
    }
    if (inicio.isAfter(fim)) {
      throw new IllegalArgumentException("Data de início não pode ser após data de fim");
    }
    return pedidoRepository.listarEntreguesEntreDatasParaCliente(cpf, inicio, fim);
  }

  public Pedido recuperarPorId(long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID do pedido deve ser maior que zero: " + id);
    }
    return pedidoRepository.recuperarPorId(id);
  }

  public void atualizarStatus(long id, Pedido.Status novoStatus) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID do pedido deve ser maior que zero: " + id);
    }
    if (novoStatus == null) {
      throw new IllegalArgumentException("Novo status não pode ser nulo");
    }
    pedidoRepository.atualizarStatus(id, novoStatus);
  }

  public void registrarPagamento(long id, LocalDateTime dataHoraPagamento) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID do pedido deve ser maior que zero: " + id);
    }
    if (dataHoraPagamento == null) {
      throw new IllegalArgumentException("Data e hora do pagamento não podem ser nulas");
    }
    pedidoRepository.registrarPagamento(id, dataHoraPagamento);
  }
}
