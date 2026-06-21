package com.pizzaria.estoque.Adaptadores.Dados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos.ItemEstoqueDTO;
import com.pizzaria.estoque.Dominio.Entidades.ItemEstoque;
import com.pizzaria.estoque.Dominio.Servicos.IEstoqueService;
import lombok.RequiredArgsConstructor;

/**
 * Implementação do serviço de estoque usando JPA
 */
@Service
@RequiredArgsConstructor
public class EstoqueService implements IEstoqueService {

	private final ItemEstoqueRepository itemEstoqueRepository;

	/**
	 * Verifica a disponibilidade de itens em estoque
	 * Retorna lista vazia se todos estão disponíveis, ou lista dos indisponíveis
	 */
	@Override
	public List<ItemEstoqueDTO> verificarEstoque(List<ItemEstoqueDTO> itens) {
		List<ItemEstoqueDTO> indisponíveis = new ArrayList<>();

		for (ItemEstoqueDTO item : itens) {
			Optional<ItemEstoque> estoque = itemEstoqueRepository.findByIngredienteId(item.getIngredienteId());

			if (estoque.isEmpty() || !estoque.get().temQuantidadeSuficiente(item.getQuantidade())) {
				item.setDisponivel(false);
				indisponíveis.add(item);
			} else {
				item.setDisponivel(true);
			}
		}

		return indisponíveis;
	}

	/**
	 * Atualiza o estoque após confirmação de um pedido
	 * Reduz as quantidades dos itens
	 */
	@Override
	public boolean atualizarEstoque(List<ItemEstoqueDTO> itens) {
		try {
			for (ItemEstoqueDTO item : itens) {
				Optional<ItemEstoque> estoque = itemEstoqueRepository.findByIngredienteId(item.getIngredienteId());

				if (estoque.isPresent()) {
					ItemEstoque itemDb = estoque.get();
					if (itemDb.reduzirQuantidade(item.getQuantidade())) {
						itemEstoqueRepository.save(itemDb);
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Obtém o item de estoque por ID de ingrediente
	 */
	@Override
	public ItemEstoque obterPorIngrediente(Long ingredienteId) {
		Optional<ItemEstoque> estoque = itemEstoqueRepository.findByIngredienteId(ingredienteId);
		return estoque.orElse(null);
	}

	/**
	 * Lista todos os itens de estoque
	 */
	@Override
	public List<ItemEstoque> listarTodos() {
		return itemEstoqueRepository.findAll();
	}
}
