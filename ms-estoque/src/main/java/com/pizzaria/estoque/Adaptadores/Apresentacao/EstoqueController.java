package com.pizzaria.estoque.Adaptadores.Apresentacao;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pizzaria.estoque.Adaptadores.Apresentacao.Dtos.ItemEstoqueDTO;
import com.pizzaria.estoque.Aplicacao.AtualizarEstoqueUC;
import com.pizzaria.estoque.Aplicacao.VerificarEstoqueUC;
import com.pizzaria.estoque.Dominio.Entidades.ItemEstoque;
import com.pizzaria.estoque.Dominio.Servicos.IEstoqueService;
import lombok.RequiredArgsConstructor;

/**
 * Controller REST para gerenciar operações de estoque
 * Endpoints disponíveis em /api/v1/estoque
 */
@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

	private final VerificarEstoqueUC verificarEstoqueUC;
	private final AtualizarEstoqueUC atualizarEstoqueUC;
	private final IEstoqueService estoqueService;

	/**
	 * Verifica a disponibilidade de itens em estoque
	 * POST /api/v1/estoque/verificar
	 * Body: lista de ItemEstoqueDTO
	 * Retorna: lista vazia se tudo ok, ou lista dos itens indisponíveis
	 */
	@PostMapping("/verificar")
	public ResponseEntity<List<ItemEstoqueDTO>> verificarEstoque(@RequestBody List<ItemEstoqueDTO> itens) {
		List<ItemEstoqueDTO> indisponíveis = verificarEstoqueUC.executar(itens);
		
		if (indisponíveis.isEmpty()) {
			return ResponseEntity.ok(indisponíveis);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(indisponíveis);
	}

	/**
	 * Atualiza o estoque (reduz quantidades após confirmação de pedido)
	 * POST /api/v1/estoque/atualizar
	 * Body: lista de ItemEstoqueDTO
	 */
	@PostMapping("/atualizar")
	public ResponseEntity<String> atualizarEstoque(@RequestBody List<ItemEstoqueDTO> itens) {
		boolean sucesso = atualizarEstoqueUC.executar(itens);
		
		if (sucesso) {
			return ResponseEntity.ok("Estoque atualizado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao atualizar estoque");
	}

	/**
	 * Lista todos os itens em estoque
	 * GET /api/v1/estoque/listar
	 */
	@GetMapping("/listar")
	public ResponseEntity<List<ItemEstoque>> listarEstoque() {
		List<ItemEstoque> itens = estoqueService.listarTodos();
		return ResponseEntity.ok(itens);
	}

	/**
	 * Obtém um item de estoque pelo ID do ingrediente
	 * GET /api/v1/estoque/ingrediente/{ingredienteId}
	 */
	@GetMapping("/ingrediente/{ingredienteId}")
	public ResponseEntity<ItemEstoque> obterPorIngrediente(@PathVariable Long ingredienteId) {
		ItemEstoque item = estoqueService.obterPorIngrediente(ingredienteId);
		
		if (item != null) {
			return ResponseEntity.ok(item);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
