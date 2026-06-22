package com.pizzaria.entregas.Dominio.Servicos;

import java.util.List;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;

public interface IEntregaService {
	Entrega criarEntrega(Entrega entrega);
	Entrega buscarPorId(Long id);
	Entrega buscarPorPedidoId(Long pedidoId);
	List<Entrega> listarTodas();
	Entrega atualizarStatus(Long id, Entrega.Status novoStatus);
}
