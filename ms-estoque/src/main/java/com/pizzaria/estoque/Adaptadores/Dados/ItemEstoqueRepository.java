package com.pizzaria.estoque.Adaptadores.Dados;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pizzaria.estoque.Dominio.Entidades.ItemEstoque;

/**
 * Repository JPA para persistência de ItemEstoque
 */
@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {

	/**
	 * Encontra um item de estoque pelo ID do ingrediente
	 * @param ingredienteId ID do ingrediente
	 * @return Optional contendo o ItemEstoque encontrado
	 */
	Optional<ItemEstoque> findByIngredienteId(Long ingredienteId);

	/**
	 * Verifica se existe item de estoque para um ingrediente
	 * @param ingredienteId ID do ingrediente
	 * @return true se existe
	 */
	boolean existsByIngredienteId(Long ingredienteId);
}
