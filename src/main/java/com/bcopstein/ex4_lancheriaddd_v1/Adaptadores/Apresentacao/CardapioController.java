package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;
// Controller REST que expõe endpoints GET /cardapio para consulta de cardápios e seus produtos

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.CabecalhoCardapioPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.CardapioPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaListaCardapiosUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperarCardapioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CardapioResponse;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {

  private final RecuperarCardapioUC recuperaCardapioUC;
  private final RecuperaListaCardapiosUC recuperaListaCardapioUC;

  public CardapioController(RecuperarCardapioUC recuperaCardapioUC,
               RecuperaListaCardapiosUC recuperaListaCardapioUC) {
    this.recuperaCardapioUC = recuperaCardapioUC;
    this.recuperaListaCardapioUC = recuperaListaCardapioUC;
  }

  @GetMapping("/{id}")
  @CrossOrigin("*")
  public CardapioPresenter recuperaCardapio(@PathVariable(value = "id") long id) {
    return toPresenter(recuperaCardapioUC.run(id));
  }

  @GetMapping("/lista")
  @CrossOrigin("*")
  // Retorna a lista de cabeçalhos (id e título) de todos os cardápios disponíveis
  public List<CabecalhoCardapioPresenter> recuperaListaCardapios() {
    return recuperaListaCardapioUC.run().cabecalhos().stream()
        .map(c -> new CabecalhoCardapioPresenter(c.id(), c.titulo()))
        .toList();
  }

  @GetMapping("")
  @CrossOrigin("*")
  // Retorna o cardápio padrão (ID 1) quando nenhum ID é informado na URL
  public CardapioPresenter recuperaCardapioSemId() {
    return toPresenter(recuperaCardapioUC.run());
  }

  // Converte o CardapioResponse em CardapioPresenter, marcando os produtos indicados pelo chef
  private CardapioPresenter toPresenter(CardapioResponse response) {
    CardapioPresenter presenter = new CardapioPresenter(response.getTitulo());
    for (CardapioResponse.ItemCardapioDTO item : response.getProdutos()) {
      boolean sugestao = response.getIdsDoChef().contains(item.id());
      presenter.insereItem(item.id(), item.descricao(), item.preco(), sugestao);
    }
    return presenter;
  }
}
