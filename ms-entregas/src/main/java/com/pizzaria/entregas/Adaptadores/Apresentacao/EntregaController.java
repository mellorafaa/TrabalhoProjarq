package com.pizzaria.entregas.Adaptadores.Apresentacao;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pizzaria.entregas.Adaptadores.Apresentacao.Dtos.EntregaDTO;
import com.pizzaria.entregas.Aplicacao.AtualizarStatusEntregaUC;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;
import com.pizzaria.entregas.Dominio.Servicos.IEntregaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entregas")
@RequiredArgsConstructor
public class EntregaController {

	private final IEntregaService entregaService;
	private final AtualizarStatusEntregaUC atualizarStatusEntregaUC;

	@GetMapping("/listar")
	public ResponseEntity<List<EntregaDTO>> listarEntregas() {
		List<EntregaDTO> entregas = entregaService.listarTodas()
			.stream()
			.map(EntregaDTO::de)
			.toList();
		return ResponseEntity.ok(entregas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntregaDTO> buscarPorId(@PathVariable Long id) {
		Entrega entrega = entregaService.buscarPorId(id);
		if (entrega == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(EntregaDTO.de(entrega));
	}

	@GetMapping("/pedido/{pedidoId}")
	public ResponseEntity<EntregaDTO> buscarPorPedido(@PathVariable Long pedidoId) {
		Entrega entrega = entregaService.buscarPorPedidoId(pedidoId);
		if (entrega == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(EntregaDTO.de(entrega));
	}

	@PostMapping("/{id}/em-transporte")
	public ResponseEntity<EntregaDTO> iniciarTransporte(@PathVariable Long id) {
		Entrega entrega = atualizarStatusEntregaUC.executar(id, Entrega.Status.EM_TRANSPORTE);
		if (entrega == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(EntregaDTO.de(entrega));
	}

	@PostMapping("/{id}/entregar")
	public ResponseEntity<EntregaDTO> concluirEntrega(@PathVariable Long id) {
		Entrega entrega = atualizarStatusEntregaUC.executar(id, Entrega.Status.ENTREGUE);
		if (entrega == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(EntregaDTO.de(entrega));
	}
}
