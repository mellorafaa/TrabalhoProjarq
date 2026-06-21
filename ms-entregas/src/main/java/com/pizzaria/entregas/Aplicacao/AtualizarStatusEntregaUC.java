package com.pizzaria.entregas.Aplicacao;

import org.springframework.stereotype.Component;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;
import com.pizzaria.entregas.Dominio.Servicos.IEntregaService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AtualizarStatusEntregaUC {

	private final IEntregaService entregaService;

	public Entrega executar(Long entregaId, Entrega.Status novoStatus) {
		return entregaService.atualizarStatus(entregaId, novoStatus);
	}
}
