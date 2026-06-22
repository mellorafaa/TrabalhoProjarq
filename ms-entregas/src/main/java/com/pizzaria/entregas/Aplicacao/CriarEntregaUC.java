package com.pizzaria.entregas.Aplicacao;

import org.springframework.stereotype.Component;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;
import com.pizzaria.entregas.Dominio.Servicos.IEntregaService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CriarEntregaUC {

	private final IEntregaService entregaService;

	public Entrega executar(Long pedidoId, String clienteCpf, String clienteNome, String enderecoEntrega) {
		Entrega entrega = Entrega.builder()
			.pedidoId(pedidoId)
			.clienteCpf(clienteCpf)
			.clienteNome(clienteNome)
			.enderecoEntrega(enderecoEntrega)
			.build();
		return entregaService.criarEntrega(entrega);
	}
}
