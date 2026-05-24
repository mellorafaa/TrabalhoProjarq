package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;
// Classe CardapioRepository: responsabilidade principal inferida pelo nome 

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

//Interface de repositório de cardápios; define operações de acesso a dados de cardápio
public interface CardapioRepository {
  //Retorna lista de cabeçalhos de todos os cardápios disponíveis
  List<CabecalhoCardapio> cardapiosDisponiveis();

  //Recupera um cardápio completo pelo seu ID
  Cardapio recuperaPorId(long id);

  //Retorna a lista de produtos indicados pelo chef
  List<Produto> indicacoesDoChef();
}
