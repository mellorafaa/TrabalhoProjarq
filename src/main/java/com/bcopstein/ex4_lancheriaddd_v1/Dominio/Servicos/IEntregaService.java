package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe IEntregaService: responsabilidade principal inferida pelo nome 

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

//Interface do serviço de entrega; define operações do fluxo de entrega dos pedidos
public interface IEntregaService {
  //Notifica o serviço de entrega da chegada de um pedido pronto para ser transportado
  void chegadaDePedido(Pedido p);

  //Marca o pedido em transporte como entregue ao cliente
  void pedidoEntregue();
}
