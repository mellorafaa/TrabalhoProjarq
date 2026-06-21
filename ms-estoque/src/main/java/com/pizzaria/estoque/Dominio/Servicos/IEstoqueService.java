package com.pizzaria.estoque.Dominio.Servicos;

import java.util.List;
import com.pizzaria.estoque.Dominio.Entidades.ItemEstoque;
import com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos.ItemEstoqueDTO;

/**
 * Interface que define os serviços de domínio para gerenciar estoque
 */
public interface IEstoqueService {

	/**
	 * Verifica a disponibilidade de itens em estoque
	 * @param itens lista de itens solicitados
	 * @return lista de itens indisponíveis (vazia se todos estão disponíveis)
	 */
	List<ItemEstoqueDTO> verificarEstoque(List<ItemEstoqueDTO> itens);

	/**
	 * Atualiza o estoque após confirmação de um pedido
	 * @param itens lista de itens a serem retirados do estoque
	 * @return true se conseguiu atualizar, false caso contrário
	 */
	boolean atualizarEstoque(List<ItemEstoqueDTO> itens);

	/**
	 * Obtém o item de estoque por ID de ingrediente
	 * @param ingredienteId ID do ingrediente
	 * @return ItemEstoque encontrado ou null
	 */
	ItemEstoque obterPorIngrediente(Long ingredienteId);

	/**
	 * Lista todos os itens de estoque
	 * @return lista de itens em estoque
	 */
	List<ItemEstoque> listarTodos();
}
