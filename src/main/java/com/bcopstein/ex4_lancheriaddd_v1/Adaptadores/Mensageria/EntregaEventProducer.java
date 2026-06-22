package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Mensageria;

import java.time.LocalDateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Mensageria.Dtos.PedidoPagoEventDTO;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEntregaNotificacaoService;

@Component
public class EntregaEventProducer implements IEntregaNotificacaoService {

	private final RabbitTemplate rabbitTemplate;

	public EntregaEventProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void notificarPedidoPago(long pedidoId, String clienteCpf, String clienteNome,
			String enderecoEntrega, LocalDateTime dataPagamento) {
		PedidoPagoEventDTO evento = new PedidoPagoEventDTO(
			pedidoId, clienteCpf, clienteNome, enderecoEntrega, dataPagamento
		);
		rabbitTemplate.convertAndSend(
			RabbitMQConfig.EXCHANGE_PEDIDOS_PAGOS,
			RabbitMQConfig.ROUTING_KEY_PEDIDO_PAGO,
			evento
		);
	}
}
