package com.pizzaria.estoque.Aplicacao;

import java.util.List;
import org.springframework.stereotype.Service;
import com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos.ItemEstoqueDTO;
import com.pizzaria.estoque.Dominio.Servicos.IEstoqueService;
import lombok.RequiredArgsConstructor;

/**
 * Use Case: Verificar disponibilidade de estoque para um pedido
 * Recebe uma lista de itens e retorna quais não estão disponíveis
 */
@Service
@RequiredArgsConstructor
public class VerificarEstoqueUC {

	private final IEstoqueService estoqueService;

	/**
	 * Executa a verificação de estoque
	 * @param itens lista de itens a verificar
	 * @return lista vazia se todos disponíveis, ou lista dos indisponíveis
	 */
	public List<ItemEstoqueDTO> executar(List<ItemEstoqueDTO> itens) {
		return estoqueService.verificarEstoque(itens);
	}
}
