package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;

@Service
public class EstoqueServiceRest implements IEstoqueService {

  private final RestTemplate restTemplate;

  @Value("${ms-estoque.url:http://localhost:8082/api/v1}")
  private String msEstoqueUrl;

  public EstoqueServiceRest(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<ItemPedido> verificarEstoque(List<ItemPedido> itens) {
    List<EstoqueItemDTO> ingredientesParaVerificar = montarListaIngredientes(itens);

    if (ingredientesParaVerificar.isEmpty()) {
      return List.of();
    }

    try {
      ResponseEntity<EstoqueItemDTO[]> response = restTemplate.postForEntity(
          msEstoqueUrl + "/estoque/verificar",
          ingredientesParaVerificar,
          EstoqueItemDTO[].class
      );

      EstoqueItemDTO[] indisponiveis = response.getBody();
      if (indisponiveis == null || indisponiveis.length == 0) {
        return List.of();
      }

      // Retorna os itens do pedido cujos ingredientes estão indisponíveis
      return itens;

    } catch (Exception e) {
      // ms-estoque indisponível: aprova pedido para não bloquear o sistema
      return List.of();
    }
  }

  private List<EstoqueItemDTO> montarListaIngredientes(List<ItemPedido> itens) {
    List<EstoqueItemDTO> dtos = new ArrayList<>();
    for (ItemPedido item : itens) {
      List<Ingrediente> ingredientes = item.getItem().getReceita().getIngredientes();
      for (Ingrediente ingrediente : ingredientes) {
        dtos.add(new EstoqueItemDTO(ingrediente.getId(), item.getQuantidade()));
      }
    }
    return dtos;
  }

  // DTO interno para comunicação com ms-estoque
  public static class EstoqueItemDTO {
    private Long ingredienteId;
    private int quantidade;

    public EstoqueItemDTO() {}

    public EstoqueItemDTO(Long ingredienteId, int quantidade) {
      this.ingredienteId = ingredienteId;
      this.quantidade = quantidade;
    }

    public Long getIngredienteId() { return ingredienteId; }
    public int getQuantidade() { return quantidade; }
    public void setIngredienteId(Long ingredienteId) { this.ingredienteId = ingredienteId; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
  }
}
