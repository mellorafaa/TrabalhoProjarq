package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;
// Classe RecuperarCardapioUC: responsabilidade principal inferida pelo nome 

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

  // Método run: public run — descrição breve 
  public CardapioResponse run(long idCardapio) {
    Cardapio cardapio = cardapioService.recuperaCardapio(idCardapio);
    List<Produto> sugestoes = cardapioService.recuperaSugestoesDoChef();
    return new CardapioResponse(cardapio, sugestoes);
  }

  // Método run: public run — descrição breve 
  public CardapioResponse run() {
    Cardapio cardapio = cardapioService.recuperaCardapioSemId();
    List<Produto> sugestoes = cardapioService.recuperaSugestoesDoChef();
    return new CardapioResponse(cardapio, sugestoes);
  }
}
