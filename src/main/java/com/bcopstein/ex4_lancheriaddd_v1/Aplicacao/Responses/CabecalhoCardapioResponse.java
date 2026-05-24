package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
import java.util.List;

//Resposta contendo a lista de cabeçalhos (id e título) dos cardápios disponíveis
public record CabecalhoCardapioResponse(List<CabecalhoDTO> cabecalhos) {
  public record CabecalhoDTO(long id, String titulo) {}
}
