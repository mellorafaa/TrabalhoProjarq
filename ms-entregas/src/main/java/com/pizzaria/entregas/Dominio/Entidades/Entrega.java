package com.pizzaria.entregas.Dominio.Entidades;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entregas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long pedidoId;
	private String clienteCpf;
	private String clienteNome;
	private String enderecoEntrega;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime dataCriacao;
	private LocalDateTime dataEntrega;

	public enum Status {
		PENDENTE, EM_TRANSPORTE, ENTREGUE
	}
}
