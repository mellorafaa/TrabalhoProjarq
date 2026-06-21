package com.pizzaria.entregas.Adaptadores.Dados;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pizzaria.entregas.Dominio.Entidades.Entrega;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
	Optional<Entrega> findByPedidoId(Long pedidoId);
}
