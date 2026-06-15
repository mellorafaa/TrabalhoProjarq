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
@Service
public class PedidoService {

  private final PedidoRepository pedidoRepository;

  //Injeta o repositório de pedidos
  public PedidoService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  //Cria e aprova um pedido com os valores calculados, persistindo no repositório
  public Pedido criarEAprovarPedido(Cliente cliente, String enderecoEntrega,
                   List<ItemPedido> itens, ValorPedidoDto valores) {
    Pedido pedido = new Pedido(
        0, cliente, enderecoEntrega, null,
        itens, Pedido.Status.NOVO,
        0, 0, 0, 0
    );
    pedido.aprovar(valores.subtotal, valores.impostos, valores.desconto, valores.valorCobrado);
    return pedidoRepository.salvar(pedido);
  }

  public List<Pedido> listarTodos() {
    return pedidoRepository.listarTodos();
  }

  public List<Pedido> listarPorClienteCpf(String cpf) {
    return pedidoRepository.listarPorClienteCpf(cpf);
  }

  public List<Pedido> listarEntreguesEntreDatas(LocalDate inicio, LocalDate fim) {
    return pedidoRepository.listarEntreguesEntreDatas(inicio, fim);
  }

  public List<Pedido> listarEntreguesEntreDatasParaCliente(String cpf, LocalDate inicio, LocalDate fim) {
    return pedidoRepository.listarEntreguesEntreDatasParaCliente(cpf, inicio, fim);
  }

  public Pedido recuperarPorId(long id) {
    return pedidoRepository.recuperarPorId(id);
  }

  public void atualizarStatus(long id, Pedido.Status novoStatus) {
    pedidoRepository.atualizarStatus(id, novoStatus);
  }

  public void registrarPagamento(long id, LocalDateTime dataHoraPagamento) {
    pedidoRepository.registrarPagamento(id, dataHoraPagamento);
  }
}
