package com.pizzaria.estoque.Adaptadores.Mensageria;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.pizzaria.estoque.Adaptadores.Mensageria.Dtos.EstoqueValidacaoEventDTO;
import com.pizzaria.estoque.Adaptadores.Mensageria.Dtos.EstoqueValidacaoEventDTO.ItemIndisponiveDTO;
import com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos.ItemEstoqueDTO;
import com.pizzaria.estoque.Dominio.Entidades.ItemEstoque;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Producer: Envia mensagens de estoque para o RabbitMQ
 * Responsável por notificar ms-pedidos sobre validação de estoque
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EstoqueProducer {

	private final RabbitTemplate rabbitTemplate;

	/**
	 * Envia mensagem de estoque validado (sucesso)
	 * @param pedidoId ID do pedido
	 * @param itens itens que foram validados
	 */
	public void enviarEstoqueValidado(Long pedidoId, List<ItemEstoqueDTO> itens) {
		log.info("Enviando confirmação de estoque para pedido: {}", pedidoId);

		EstoqueValidacaoEventDTO evento = EstoqueValidacaoEventDTO.builder()
			.pedidoId(pedidoId)
			.validado(true)
			.itensIndisponíveis(List.of())
			.dataValidacao(LocalDateTime.now())
			.mensagem("Todos os itens estão disponíveis em estoque")
			.build();

		rabbitTemplate.convertAndSend(
			RabbitMQConfig.EXCHANGE_ESTOQUE,
			RabbitMQConfig.ROUTING_KEY_ESTOQUE_OK,
			evento
		);
	}

	/**
	 * Envia mensagem de estoque inválido (erro)
	 * @param pedidoId ID do pedido
	 * @param itensIndisponíveis itens que não estão disponíveis
	 * @param itensEstoque todos os itens do estoque (para referência)
	 */
	public void enviarEstoqueInvalido(Long pedidoId, List<ItemEstoqueDTO> itensIndisponíveis) {
		log.warn("Enviando erro de estoque para pedido: {}", pedidoId);

		List<ItemIndisponiveDTO> itensIndis = itensIndisponíveis.stream()
			.map(item -> ItemIndisponiveDTO.builder()
				.ingredienteId(item.getIngredienteId())
				.ingredienteNome(item.getIngredienteNome())
				.quantidadeSolicitada(item.getQuantidade())
				.quantidadeDisponivel(0) // será preenchido pelo consumer
				.build())
			.toList();

		EstoqueValidacaoEventDTO evento = EstoqueValidacaoEventDTO.builder()
			.pedidoId(pedidoId)
			.validado(false)
			.itensIndisponíveis(itensIndis)
			.dataValidacao(LocalDateTime.now())
			.mensagem("Alguns itens não estão disponíveis em estoque")
			.build();

		rabbitTemplate.convertAndSend(
			RabbitMQConfig.EXCHANGE_ESTOQUE,
			RabbitMQConfig.ROUTING_KEY_ESTOQUE_ERRO,
			evento
		);
	}
}
