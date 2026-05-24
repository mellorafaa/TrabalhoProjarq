package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe ICozinhaService: responsabilidade principal inferida pelo nome 

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

//Interface do serviço de cozinha; define operações do fluxo de preparo dos pedidos
public interface ICozinhaService {
  //Notifica a cozinha da chegada de um novo pedido para preparo
  void chegadaDePedido(Pedido p);

  //Marca o pedido em preparo como pronto para entrega
  void pedidoPronto();
}
