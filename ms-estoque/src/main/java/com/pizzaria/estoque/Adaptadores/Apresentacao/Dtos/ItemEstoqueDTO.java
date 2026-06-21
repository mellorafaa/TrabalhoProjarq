package com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar um ItemEstoque na API
 * Usado em requisições de verificação e atualização de estoque
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEstoqueDTO {

	private Long id;
	private Long ingredienteId;
	private String ingredienteNome;
	private int quantidade;
	private int quantidadeMinima;
	private boolean disponivel;
}
