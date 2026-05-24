package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
import org.springframework.stereotype.Service;

//Serviço de domínio responsável pelo cálculo de impostos sobre o valor dos pedidos
@Service
public class ImpostoService implements IImpostoService {

  private static final double TAXA_IMPOSTO = 0.10;

  //Calcula o imposto de 10% sobre o subtotal do pedido
  @Override
  public double calcularImposto(double subtotal) {
    return subtotal * TAXA_IMPOSTO;
  }
}
