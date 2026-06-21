package com.pizzaria.estoque.Adaptadores.Mensageria;

import java.util.List;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos.ItemEstoqueDTO;
import com.pizzaria.estoque.Adaptadores.Mensageria.Dtos.PedidoEventDTO;
import com.pizzaria.estoque.Aplicacao.AtualizarEstoqueUC;
import com.pizzaria.estoque.Aplicacao.VerificarEstoqueUC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Consumer: Recebe mensagens de pedidos do RabbitMQ
 * Responsável por processar eventos de pedidos criados
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PedidosConsumer {

	private final VerificarEstoqueUC verificarEstoqueUC;
	private final AtualizarEstoqueUC atualizarEstoqueUC;
	private final EstoqueProducer estoqueProducer;

	/**
	 * Listener para fila de pedidos criados
	 * Quando um novo pedido é criado no ms-pedidos, esta mensagem é acionada
	 * @param pedidoEvent evento do pedido
	 */
	@RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDOS_CRIADOS)
	public void processarPedidoCriado(PedidoEventDTO pedidoEvent) {
		log.info("Recebido pedido para validação de estoque: {}", pedidoEvent.getPedidoId());

		try {
			// Converte itens do evento para DTO
			List<ItemEstoqueDTO> itens = pedidoEvent.getItens().stream()
				.map(item -> ItemEstoqueDTO.builder()
					.ingredienteId(item.getIngredienteId())
					.ingredienteNome(item.getIngredienteNome())
					.quantidade(item.getQuantidade())
					.build())
				.toList();

			// Verifica disponibilidade
			List<ItemEstoqueDTO> indisponíveis = verificarEstoqueUC.executar(itens);

			if (indisponíveis.isEmpty()) {
				log.info("Pedido {} validado com sucesso", pedidoEvent.getPedidoId());
				// Envia confirmação positiva
				estoqueProducer.enviarEstoqueValidado(pedidoEvent.getPedidoId(), itens);
			} else {
				log.warn("Pedido {} possui itens indisponíveis", pedidoEvent.getPedidoId());
				// Envia confirmação negativa com lista de indisponíveis
				estoqueProducer.enviarEstoqueInvalido(pedidoEvent.getPedidoId(), indisponíveis);
			}
		} catch (Exception e) {
			log.error("Erro ao processar pedido {}: ", pedidoEvent.getPedidoId(), e);
			List<ItemEstoqueDTO> vazio = List.of();
			estoqueProducer.enviarEstoqueInvalido(pedidoEvent.getPedidoId(), vazio);
		}
	}

	/**
	 * Listener para fila de atualizar estoque
	 * Quando um pedido é confirmado, esta mensagem atualiza o estoque
	 * @param pedidoEvent evento do pedido confirmado
	 */
	@RabbitListener(queues = RabbitMQConfig.QUEUE_ATUALIZAR_ESTOQUE)
	public void atualizarEstoquePedido(PedidoEventDTO pedidoEvent) {
		log.info("Atualizando estoque para pedido confirmado: {}", pedidoEvent.getPedidoId());

		try {
			// Converte itens do evento para DTO
			List<ItemEstoqueDTO> itens = pedidoEvent.getItens().stream()
				.map(item -> ItemEstoqueDTO.builder()
					.ingredienteId(item.getIngredienteId())
					.quantidade(item.getQuantidade())
					.build())
				.toList();

			// Atualiza estoque
			boolean sucesso = atualizarEstoqueUC.executar(itens);

			if (sucesso) {
				log.info("Estoque atualizado com sucesso para pedido {}", pedidoEvent.getPedidoId());
			} else {
				log.error("Falha ao atualizar estoque para pedido {}", pedidoEvent.getPedidoId());
			}
		} catch (Exception e) {
			log.error("Erro ao atualizar estoque para pedido {}: ", pedidoEvent.getPedidoId(), e);
		}
	}
}
