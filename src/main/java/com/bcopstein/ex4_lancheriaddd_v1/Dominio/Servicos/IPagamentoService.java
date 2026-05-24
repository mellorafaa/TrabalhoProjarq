package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe IPagamentoService: responsabilidade principal inferida pelo nome 

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

//Interface do serviço de pagamento; define o contrato de processamento de pagamento de pedidos
public interface IPagamentoService {

  //Processa o pagamento do pedido; retorna true se autorizado, false caso contrário
  boolean processarPagamento(Pedido pedido);
}
