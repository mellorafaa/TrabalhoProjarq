package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.time.LocalDateTime;

public interface IEntregaNotificacaoService {
	void notificarPedidoPago(long pedidoId, String clienteCpf, String clienteNome,
			String enderecoEntrega, LocalDateTime dataPagamento);
}
