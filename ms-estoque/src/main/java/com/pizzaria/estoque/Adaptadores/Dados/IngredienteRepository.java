package com.pizzaria.estoque.Adaptadores.Dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pizzaria.estoque.Dominio.Entidades.Ingrediente;

/**
 * Repository JPA para persistência de Ingredientes
 */
@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

	/**
	 * Encontra um ingrediente pelo nome
	 * @param nome nome do ingrediente
	 * @return Ingrediente encontrado ou null
	 */
	Ingrediente findByNome(String nome);
}
