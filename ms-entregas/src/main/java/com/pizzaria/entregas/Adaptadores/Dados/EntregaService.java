package com.pizzaria.entregas.Adaptadores.Dados;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;
import com.pizzaria.entregas.Dominio.Servicos.IEntregaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntregaService implements IEntregaService {

	private final EntregaRepository entregaRepository;

	@Override
	public Entrega criarEntrega(Entrega entrega) {
		entrega.setStatus(Entrega.Status.PENDENTE);
		entrega.setDataCriacao(LocalDateTime.now());
		return entregaRepository.save(entrega);
	}

	@Override
	public Entrega buscarPorId(Long id) {
		return entregaRepository.findById(id).orElse(null);
	}

	@Override
	public Entrega buscarPorPedidoId(Long pedidoId) {
		return entregaRepository.findByPedidoId(pedidoId).orElse(null);
	}

	@Override
	public List<Entrega> listarTodas() {
		return entregaRepository.findAll();
	}

	@Override
	public Entrega atualizarStatus(Long id, Entrega.Status novoStatus) {
		Optional<Entrega> opt = entregaRepository.findById(id);
		if (opt.isEmpty()) {
			return null;
		}
		Entrega entrega = opt.get();
		entrega.setStatus(novoStatus);
		if (novoStatus == Entrega.Status.ENTREGUE) {
			entrega.setDataEntrega(LocalDateTime.now());
		}
		return entregaRepository.save(entrega);
	}
}
