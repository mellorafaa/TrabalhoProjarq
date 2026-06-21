package com.pizzaria.estoque.Dominio.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de domínio que representa um ingrediente no estoque
 * Persiste em banco de dados via JPA
 */
@Entity
@Table(name = "ingredientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String descricao;
	private double precoPorUnidade;
	private String unidadeMedida; // kg, litro, unidade, etc

	/**
	 * Cria um novo ingrediente com dados iniciais
	 */
	public static Ingrediente criar(String nome, String descricao, double precoPorUnidade, String unidadeMedida) {
		return Ingrediente.builder()
			.nome(nome)
			.descricao(descricao)
			.precoPorUnidade(precoPorUnidade)
			.unidadeMedida(unidadeMedida)
			.build();
	}
}
