package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Mensageria.Dtos;

import java.time.LocalDateTime;

public class PedidoPagoEventDTO {

	private Long pedidoId;
	private String clienteCpf;
	private String clienteNome;
	private String enderecoEntrega;
	private LocalDateTime dataPagamento;

	public PedidoPagoEventDTO() {}

	public PedidoPagoEventDTO(Long pedidoId, String clienteCpf, String clienteNome,
			String enderecoEntrega, LocalDateTime dataPagamento) {
		this.pedidoId = pedidoId;
		this.clienteCpf = clienteCpf;
		this.clienteNome = clienteNome;
		this.enderecoEntrega = enderecoEntrega;
		this.dataPagamento = dataPagamento;
	}

	public Long getPedidoId() { return pedidoId; }
	public String getClienteCpf() { return clienteCpf; }
	public String getClienteNome() { return clienteNome; }
	public String getEnderecoEntrega() { return enderecoEntrega; }
	public LocalDateTime getDataPagamento() { return dataPagamento; }
}
