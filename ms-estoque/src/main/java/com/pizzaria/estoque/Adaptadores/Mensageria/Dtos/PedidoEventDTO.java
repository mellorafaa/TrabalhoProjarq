package com.pizzaria.estoque.Adaptadores.Mensageria.Dtos;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar um pedido na fila de mensagens
 * Enviado pelo ms-pedidos ao ms-estoque
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoEventDTO {

	private Long pedidoId;
	private Long clienteId;
	private List<ItemPedidoEventDTO> itens;
	private LocalDateTime dataCriacao;
	private String status;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ItemPedidoEventDTO {
		private Long ingredienteId;
		private String ingredienteNome;
		private int quantidade;
	}
}
