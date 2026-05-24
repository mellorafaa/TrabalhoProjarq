package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;
// Implementação JDBC do repositório de cardápios; consulta cardápios e produtos no banco de dados

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.CardapioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Component
public class CardapioRepositoryJDBC implements CardapioRepository {
  private final JdbcTemplate jdbcTemplate;
  private final ProdutosRepository produtosRepository;

  public CardapioRepositoryJDBC(JdbcTemplate jdbcTemplate, ProdutosRepository produtosRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.produtosRepository = produtosRepository;
  }

  @Override
  // Busca um cardápio pelo ID, carregando os produtos associados
  public Cardapio recuperaPorId(long id) {
    String sql = "SELECT id, titulo FROM cardapios WHERE id = ?";
    List<Cardapio> cardapios = this.jdbcTemplate.query(
      sql,
      ps -> ps.setLong(1, id),
      (rs, rowNum) -> new Cardapio(new CabecalhoCardapio(rs.getLong("id"), rs.getString("titulo")), null)
    );
    if (cardapios.isEmpty()) {
      return null;
    }
    Cardapio cardapio = cardapios.getFirst();
    List<Produto> produtos = produtosRepository.recuperaProdutosCardapio(id);
    cardapio.setProdutos(produtos);
    return cardapio;
  }

  @Override
  // Retorna os produtos indicados pelo chef (atualmente fixo: produto de ID 2)
  public List<Produto> indicacoesDoChef() {
    return List.of(produtosRepository.recuperaProdutoPorId(2L));  
  }

  @Override
  // Retorna os cabeçalhos de todos os cardápios cadastrados na tabela cardapios
  public List<CabecalhoCardapio> cardapiosDisponiveis(){
    String sql = "SELECT id, titulo FROM cardapios";
    List<CabecalhoCardapio> cabCardapios = this.jdbcTemplate.query(
      sql,
      ps->{},
      (rs, rowNum) -> new CabecalhoCardapio(rs.getLong("id"), rs.getString("titulo"))
    );
    return cabCardapios;
  }
  
}
