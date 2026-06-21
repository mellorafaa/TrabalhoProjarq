package com.pizzaria.estoque.Adaptadores.Mensageria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de RabbitMQ para o microsserviço de estoque
 * Define filas, exchanges e bindings necessários
 */
@Configuration
public class RabbitMQConfig {

	// ═══════════════════════════════════════════════════════════════════════════
	// FILAS E EXCHANGES PARA RECEBER MENSAGENS (Consumer)
	// ═══════════════════════════════════════════════════════════════════════════

	// Fila: ms-estoque recebe pedidos criados do ms-pedidos
	public static final String QUEUE_PEDIDOS_CRIADOS = "estoque.pedidos.criados";
	public static final String EXCHANGE_PEDIDOS = "pedidos.exchange";
	public static final String ROUTING_KEY_PEDIDOS = "pedidos.criar";

	@Bean
	public Queue filaPedidosCriados() {
		return new Queue(QUEUE_PEDIDOS_CRIADOS, true);
	}

	@Bean
	public DirectExchange exchangePedidos() {
		return new DirectExchange(EXCHANGE_PEDIDOS, true, false);
	}

	@Bean
	public Binding bindingPedidosCriados(Queue filaPedidosCriados, DirectExchange exchangePedidos) {
		return BindingBuilder.bind(filaPedidosCriados)
			.to(exchangePedidos)
			.with(ROUTING_KEY_PEDIDOS);
	}

	// ═══════════════════════════════════════════════════════════════════════════
	// FILAS E EXCHANGES PARA ENVIAR MENSAGENS (Producer)
	// ═══════════════════════════════════════════════════════════════════════════

	// Fila: ms-estoque envia resposta de validação para ms-pedidos
	public static final String QUEUE_ESTOQUE_VALIDADO = "estoque.validado";
	public static final String EXCHANGE_ESTOQUE = "estoque.exchange";
	public static final String ROUTING_KEY_ESTOQUE_OK = "estoque.validado.ok";
	public static final String ROUTING_KEY_ESTOQUE_ERRO = "estoque.validado.erro";

	@Bean
	public Queue filaEstoqueValidado() {
		return new Queue(QUEUE_ESTOQUE_VALIDADO, true);
	}

	@Bean
	public DirectExchange exchangeEstoque() {
		return new DirectExchange(EXCHANGE_ESTOQUE, true, false);
	}

	@Bean
	public Binding bindingEstoqueValidado(Queue filaEstoqueValidado, DirectExchange exchangeEstoque) {
		return BindingBuilder.bind(filaEstoqueValidado)
			.to(exchangeEstoque)
			.with(ROUTING_KEY_ESTOQUE_OK);
	}

	// ═══════════════════════════════════════════════════════════════════════════
	// FILAS PARA ATUALIZAR ESTOQUE (Consumer)
	// ═══════════════════════════════════════════════════════════════════════════

	// Fila: ms-estoque recebe confirmação para atualizar estoque
	public static final String QUEUE_ATUALIZAR_ESTOQUE = "estoque.atualizar";
	public static final String ROUTING_KEY_ATUALIZAR_ESTOQUE = "estoque.atualizar";

	@Bean
	public Queue filaAtualizarEstoque() {
		return new Queue(QUEUE_ATUALIZAR_ESTOQUE, true);
	}

	@Bean
	public Binding bindingAtualizarEstoque(Queue filaAtualizarEstoque, DirectExchange exchangePedidos) {
		return BindingBuilder.bind(filaAtualizarEstoque)
			.to(exchangePedidos)
			.with(ROUTING_KEY_ATUALIZAR_ESTOQUE);
	}
}
