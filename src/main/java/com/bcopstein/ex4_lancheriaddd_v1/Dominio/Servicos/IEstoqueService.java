package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;

//Interface do serviço de estoque; define o contrato de verificação de disponibilidade de ingredientes
public interface IEstoqueService {

  //Verifica os itens do pedido no estoque e retorna os itens indisponíveis
  List<ItemPedido> verificarEstoque(List<ItemPedido> itens);
}
