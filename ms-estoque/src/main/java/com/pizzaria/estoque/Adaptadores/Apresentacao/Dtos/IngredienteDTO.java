package com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar um Ingrediente na API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredienteDTO {

	private Long id;
	private String nome;
	private String descricao;
	private double precoPorUnidade;
	private String unidadeMedida;
}
