package com.pizzaria.estoque.Adaptadores.Mensageria.Dtos;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar a resposta de validação de estoque
 * Enviado pelo ms-estoque ao ms-pedidos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstoqueValidacaoEventDTO {

	private Long pedidoId;
	private boolean validado;
	private List<ItemIndisponiveDTO> itensIndisponíveis;
	private LocalDateTime dataValidacao;
	private String mensagem;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ItemIndisponiveDTO {
		private Long ingredienteId;
		private String ingredienteNome;
		private int quantidadeSolicitada;
		private int quantidadeDisponivel;
	}
}
