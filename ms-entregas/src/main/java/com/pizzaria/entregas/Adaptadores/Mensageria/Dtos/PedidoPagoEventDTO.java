package com.pizzaria.entregas.Adaptadores.Mensageria.Dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoPagoEventDTO {
	private Long pedidoId;
	private String clienteCpf;
	private String clienteNome;
	private String enderecoEntrega;
	private LocalDateTime dataPagamento;
}
