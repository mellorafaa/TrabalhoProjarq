package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna a lista de cabeçalhos (id e título) dos cardápios disponíveis

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CabecalhoCardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CardapioService;

@Component
public class RecuperaListaCardapiosUC {

  private final CardapioService cardapioService;

  public RecuperaListaCardapiosUC(CardapioService cardapioService) {
    this.cardapioService = cardapioService;
  }

  // Recupera e converte os cabeçalhos dos cardápios em CabecalhoCardapioResponse
  public CabecalhoCardapioResponse run() {
    return new CabecalhoCardapioResponse(
        cardapioService.recuperaListaDeCardapios().stream()
            .map(c -> new CabecalhoCardapioResponse.CabecalhoDTO(c.id(), c.titulo()))
            .toList()
    );
  }
}
