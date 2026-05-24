package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;
// Classe ProdutosRepository: responsabilidade principal inferida pelo nome 

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

//Interface de repositório de produtos; define operações de acesso a dados de produto
public interface ProdutosRepository {
  //Recupera um produto pelo seu ID; retorna null se não encontrado
  Produto recuperaProdutoPorId(long id);

  //Retorna a lista de produtos associados a um cardápio pelo ID do cardápio
  List<Produto> recuperaProdutosCardapio(long id);
}
