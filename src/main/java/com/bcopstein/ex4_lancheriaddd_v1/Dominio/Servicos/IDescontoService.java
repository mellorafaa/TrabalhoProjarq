package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe IDescontoService: responsabilidade principal inferida pelo nome 

//Interface do serviço de desconto; define o contrato de cálculo de desconto para pedidos
public interface IDescontoService {

  //Calcula o valor do desconto a aplicar ao subtotal do pedido para o cliente informado
  double calcularDesconto(double subtotal, String clienteCpf);
}
