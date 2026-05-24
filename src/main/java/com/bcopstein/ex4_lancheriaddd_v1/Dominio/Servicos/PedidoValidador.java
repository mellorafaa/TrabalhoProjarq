package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.SolicitacaoItem;

//Serviço de domínio responsável pela validação dos dados de entrada antes de criar um pedido
@Service
public class PedidoValidador {

  private final ClienteRepository clienteRepository;
  private final ProdutosRepository produtosRepository;
  private final IEstoqueService estoqueService;

  //Injeta os repositórios e o serviço de estoque necessários para validação
  public PedidoValidador(
      ClienteRepository clienteRepository,
      ProdutosRepository produtosRepository,
      IEstoqueService estoqueService) {
    this.clienteRepository = clienteRepository;
    this.produtosRepository = produtosRepository;
    this.estoqueService = estoqueService;
  }

  //Busca e valida o cliente pelo CPF; lança exceção se não encontrado
  public Cliente validarCliente(String clienteCpf) {
    Cliente cliente = clienteRepository.recuperarPorCpf(clienteCpf);
    if (cliente == null) {
      throw new RuntimeException("Cliente não encontrado com CPF: " + clienteCpf);
    }
    return cliente;
  }

  //Converte as solicitações em itens de pedido, validando a existência de cada produto
  public List<ItemPedido> validarEConverterItens(List<SolicitacaoItem> itensSolicitados) {
    List<ItemPedido> itens = new ArrayList<>();

    for (SolicitacaoItem solicitacao : itensSolicitados) {
      Produto produto = produtosRepository.recuperaProdutoPorId(solicitacao.getProdutoId());
      if (produto == null) {
        throw new RuntimeException("Produto não encontrado com ID: " + solicitacao.getProdutoId());
      }
      itens.add(new ItemPedido(produto, solicitacao.getQuantidade()));
    }

    return itens;
  }

  //Verifica a disponibilidade dos itens no estoque e retorna os indisponíveis
  public List<ItemPedido> verificarEstoque(List<ItemPedido> itens) {
    return estoqueService.verificarEstoque(itens);
  }
}
