package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;

//Serviço de domínio que calcula descontos baseados no histórico de pedidos pagos do cliente
//Implementa política de fidelidade: 7% de desconto para clientes com 3+ pedidos em 20 dias
@Service
public class DescontoService implements IDescontoService {

  private static final double TAXA_DESCONTO = 0.07;

  private static final int MINIMO_PEDIDOS_PARA_DESCONTO = 3;

  private static final int DIAS_HISTORICO = 20;

  private final PedidoRepository pedidoRepository;

  // Injeta o repositório de pedidos necessário para consultar o histórico do
  // cliente
  public DescontoService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  // Calcula 7% de desconto se o cliente tiver 3 ou mais pedidos pagos nos últimos
  // 20 dias
  // O desconto é sempre aplicado sobre o subtotal bruto, antes dos impostos
  @Override
  public double calcularDesconto(double subtotal, String clienteCpf) {
    if (subtotal < 0) {
      throw new IllegalArgumentException("Subtotal não pode ser negativo: " + subtotal);
    }

    if (clienteCpf == null || clienteCpf.isBlank()) {
      throw new IllegalArgumentException("CPF do cliente não pode ser vazio");
    }

    LocalDateTime vinteDiasAtras = LocalDateTime.now().minusDays(DIAS_HISTORICO);

    long pedidosPagos = pedidoRepository.contarPedidosPagosPorCliente(clienteCpf, vinteDiasAtras);

    if (pedidosPagos >= MINIMO_PEDIDOS_PARA_DESCONTO) {
      return subtotal * TAXA_DESCONTO;
    }

    return 0.0;
  }
}
