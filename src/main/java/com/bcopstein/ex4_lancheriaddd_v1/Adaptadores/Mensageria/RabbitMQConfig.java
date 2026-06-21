package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Mensageria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String QUEUE_PEDIDOS_PAGOS = "entregas.pedidos.pagos";
	public static final String EXCHANGE_PEDIDOS_PAGOS = "pedidos.pagos.exchange";
	public static final String ROUTING_KEY_PEDIDO_PAGO = "pedido.pago";

	@Bean
	public Queue filaPedidosPagos() {
		return new Queue(QUEUE_PEDIDOS_PAGOS, true);
	}

	@Bean
	public DirectExchange exchangePedidosPagos() {
		return new DirectExchange(EXCHANGE_PEDIDOS_PAGOS, true, false);
	}

	@Bean
	public Binding bindingPedidosPagos(Queue filaPedidosPagos, DirectExchange exchangePedidosPagos) {
		return BindingBuilder.bind(filaPedidosPagos)
			.to(exchangePedidosPagos)
			.with(ROUTING_KEY_PEDIDO_PAGO);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}
}
