package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Caso de uso que retorna um cardápio completo com seus produtos e as indicações do chef

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CardapioService;

@Component
public class RecuperarCardapioUC {

  private final CardapioService cardapioService;

  public RecuperarCardapioUC(CardapioService cardapioService) {
    this.cardapioService = cardapioService;
  }

  // Busca o cardápio pelo ID e as sugestões do chef, retornando a resposta completa
  public CardapioResponse run(long idCardapio) {
    Cardapio cardapio = cardapioService.recuperaCardapio(idCardapio);
    List<Produto> sugestoes = cardapioService.recuperaSugestoesDoChef();
    return new CardapioResponse(cardapio, sugestoes);
  }

  // Busca o cardápio padrão (sem ID) e as sugestões do chef, retornando a resposta completa
  public CardapioResponse run() {
    Cardapio cardapio = cardapioService.recuperaCardapioSemId();
    List<Produto> sugestoes = cardapioService.recuperaSugestoesDoChef();
    return new CardapioResponse(cardapio, sugestoes);
  }
}
