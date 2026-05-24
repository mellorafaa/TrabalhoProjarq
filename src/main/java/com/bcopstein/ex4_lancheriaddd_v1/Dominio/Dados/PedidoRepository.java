package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

//Interface de repositório de pedidos; define operações de acesso a dados de pedido
public interface PedidoRepository {

  //Persiste um novo pedido e retorna o objeto salvo com ID gerado
  Pedido salvar(Pedido pedido);

  //Recupera um pedido completo (com itens) pelo seu ID
  Pedido recuperarPorId(long id);

  //Retorna a lista de todos os pedidos cadastrados
  List<Pedido> listarTodos();

  //Lista os pedidos com status ENTREGUE dentro de um período de datas
  List<Pedido> listarEntreguesEntreDatas(LocalDate inicio, LocalDate fim);

  //Lista os pedidos entregues de um cliente específico dentro de um período
  List<Pedido> listarEntreguesEntreDatasParaCliente(String cpf, LocalDate inicio, LocalDate fim);

  //Conta quantos pedidos o cliente fez a partir de uma data/hora
  long contarPedidosRecentesPorCliente(String clienteCpf, LocalDateTime desde);

  //Conta quantos pedidos pagos o cliente tem a partir de uma data/hora
  long contarPedidosPagosPorCliente(String clienteCpf, LocalDateTime desde);

  //Lista todos os pedidos de um cliente pelo CPF
  List<Pedido> listarPorClienteCpf(String cpf);

  //Atualiza o status de um pedido no banco de dados
  void atualizarStatus(long id, Pedido.Status novoStatus);

  //Registra o pagamento do pedido com a data/hora atual
  void registrarPagamento(long id, LocalDateTime dataHoraPagamento);
}
