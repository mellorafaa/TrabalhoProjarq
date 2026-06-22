package com.pizzaria.estoque.Aplicacao;

import java.util.List;
import org.springframework.stereotype.Service;
import com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos.ItemEstoqueDTO;
import com.pizzaria.estoque.Dominio.Servicos.IEstoqueService;
import lombok.RequiredArgsConstructor;

/**
 * Use Case: Atualizar estoque após confirmação de pedido
 * Reduz as quantidades dos itens do estoque
 */
@Service
@RequiredArgsConstructor
public class AtualizarEstoqueUC {

	private final IEstoqueService estoqueService;

	/**
	 * Executa a atualização de estoque
	 * @param itens lista de itens a serem retirados
	 * @return true se conseguiu atualizar, false caso contrário
	 */
	public boolean executar(List<ItemEstoqueDTO> itens) {
		return estoqueService.atualizarEstoque(itens);
	}
}
