package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.stereotype.Service;

//Serviço de domínio responsável pelo cálculo de impostos sobre o valor dos pedidos
//Implementa a fórmula de imposto padrão: 10% sobre o subtotal bruto do pedido
@Service
public class ImpostoService implements IImpostoService {

  private static final double TAXA_IMPOSTO = 0.10;

  // Calcula o imposto de 10% sobre o subtotal bruto do pedido
  // Os impostos são sempre aplicados sobre o valor total dos itens, antes do
  // desconto
  @Override
  public double calcularImposto(double subtotal) {
    if (subtotal < 0) {
      throw new IllegalArgumentException("Subtotal não pode ser negativo: " + subtotal);
    }
    return subtotal * TAXA_IMPOSTO;
  }
}
