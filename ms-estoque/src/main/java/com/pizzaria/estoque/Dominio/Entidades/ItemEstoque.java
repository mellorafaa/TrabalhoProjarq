package com.pizzaria.estoque.Dominio.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de domínio que representa um item em estoque
 * Associa um ingrediente à sua quantidade disponível
 */
@Entity
@Table(name = "itens_estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ingrediente_id", nullable = false)
	private Ingrediente ingrediente;

	private int quantidade;
	private int quantidadeMinima; // quantidade mínima para reposição

	/**
	 * Reduz a quantidade de estoque
	 * @param quantidade quantidade a ser reduzida
	 * @return true se conseguiu reduzir, false se não há quantidade suficiente
	 */
	public boolean reduzirQuantidade(int quantidade) {
		if (this.quantidade >= quantidade) {
			this.quantidade -= quantidade;
			return true;
		}
		return false;
	}

	/**
	 * Aumenta a quantidade de estoque
	 * @param quantidade quantidade a ser adicionada
	 */
	public void aumentarQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}

	/**
	 * Verifica se a quantidade em estoque é suficiente
	 * @param quantidade quantidade necessária
	 * @return true se há quantidade suficiente
	 */
	public boolean temQuantidadeSuficiente(int quantidade) {
		return this.quantidade >= quantidade;
	}

	/**
	 * Verifica se está abaixo da quantidade mínima
	 * @return true se precisa reposição
	 */
	public boolean precisaReposicao() {
		return this.quantidade < this.quantidadeMinima;
	}

	/**
	 * Cria um novo item de estoque
	 */
	public static ItemEstoque criar(Ingrediente ingrediente, int quantidade, int quantidadeMinima) {
		return ItemEstoque.builder()
			.ingrediente(ingrediente)
			.quantidade(quantidade)
			.quantidadeMinima(quantidadeMinima)
			.build();
	}
}
