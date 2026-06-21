package com.pizzaria.entregas.Adaptadores.Apresentacao.Dtos;

import java.time.LocalDateTime;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntregaDTO {

	private Long id;
	private Long pedidoId;
	private String clienteCpf;
	private String clienteNome;
	private String enderecoEntrega;
	private String status;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataEntrega;

	public static EntregaDTO de(Entrega entrega) {
		return EntregaDTO.builder()
			.id(entrega.getId())
			.pedidoId(entrega.getPedidoId())
			.clienteCpf(entrega.getClienteCpf())
			.clienteNome(entrega.getClienteNome())
			.enderecoEntrega(entrega.getEnderecoEntrega())
			.status(entrega.getStatus().name())
			.dataCriacao(entrega.getDataCriacao())
			.dataEntrega(entrega.getDataEntrega())
			.build();
	}
}
