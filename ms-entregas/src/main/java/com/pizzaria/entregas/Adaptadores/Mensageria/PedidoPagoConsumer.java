package com.pizzaria.entregas.Adaptadores.Mensageria;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.pizzaria.entregas.Adaptadores.Mensageria.Dtos.PedidoPagoEventDTO;
import com.pizzaria.entregas.Aplicacao.CriarEntregaUC;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoPagoConsumer {

	private final CriarEntregaUC criarEntregaUC;

	@RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDOS_PAGOS)
	public void processarPedidoPago(PedidoPagoEventDTO evento) {
		log.info("Recebido pedido pago para entrega: pedidoId={}", evento.getPedidoId());
		try {
			Entrega entrega = criarEntregaUC.executar(
				evento.getPedidoId(),
				evento.getClienteCpf(),
				evento.getClienteNome(),
				evento.getEnderecoEntrega()
			);
			log.info("Entrega criada: id={} para pedidoId={}", entrega.getId(), evento.getPedidoId());
		} catch (Exception e) {
			log.error("Erro ao criar entrega para pedidoId={}: {}", evento.getPedidoId(), e.getMessage());
		}
	}
}
