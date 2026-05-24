package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
//Interface do serviço de imposto; define o contrato de cálculo de imposto sobre pedidos
public interface IImpostoService {

  //Calcula o valor do imposto a partir do subtotal do pedido
  double calcularImposto(double subtotal);
}
