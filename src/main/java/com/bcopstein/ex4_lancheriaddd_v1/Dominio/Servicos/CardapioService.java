package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.CardapioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

//Serviço de domínio responsável pelas operações relacionadas ao cardápio da lancheria
@Service
public class CardapioService {
  private final CardapioRepository cardapioRepository;

  //Injeta o repositório de cardápios
  public CardapioService(CardapioRepository cardapioRepository){
    this.cardapioRepository = cardapioRepository;
  }

  //Recupera o cardápio completo pelo ID informado
  public Cardapio recuperaCardapio(long Id){
    return cardapioRepository.recuperaPorId(Id);
  }

  //Retorna a lista de cabeçalhos de todos os cardápios disponíveis
  public List<CabecalhoCardapio> recuperaListaDeCardapios(){
    return cardapioRepository.cardapiosDisponiveis();
  }

  //Retorna a lista de produtos sugeridos pelo chef
  public List<Produto> recuperaSugestoesDoChef(){
    return cardapioRepository.indicacoesDoChef();
  }

  //Recupera o cardápio padrão (ID 1) quando nenhum ID é informado
  public Cardapio recuperaCardapioSemId(){
    return cardapioRepository.recuperaPorId(1);
  }
}
