package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;
// Implementação JDBC do repositório de produtos; consulta produtos e suas receitas no banco de dados

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ReceitasRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Receita;

@Component
public class ProdutosRepositoryJDBC implements ProdutosRepository {
  private final JdbcTemplate jdbcTemplate;
  private final ReceitasRepository receitasRepository;

  public ProdutosRepositoryJDBC(JdbcTemplate jdbcTemplate, ReceitasRepository receitasRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.receitasRepository = receitasRepository;
  }

  @Override
  // Busca os produtos de um cardápio pelo ID, carregando a receita de cada produto
  public List<Produto> recuperaProdutosCardapio(long id) {
    String sql = "SELECT p.id, p.descricao, p.preco, pr.receita_id " +
           "FROM produtos p " +
           "JOIN cardapio_produto cp ON p.id = cp.produto_id " +
           "JOIN produto_receita pr ON p.id = pr.produto_id " +
           "WHERE cp.cardapio_id = ?";
    List<Produto> produtos = this.jdbcTemplate.query(
      sql,
      ps -> ps.setLong(1, id),
      (rs, rowNum) -> {
        long produtoId = rs.getLong("id");
        String descricao = rs.getString("descricao");
        int preco = rs.getInt("preco");
        long receitaId = rs.getLong("receita_id");
        Receita receita = receitasRepository.recuperaReceita(receitaId);
        return new Produto(produtoId, descricao, receita, preco);
      }
    );
    return produtos;
  }

  @Override
  // Busca um produto pelo ID, incluindo sua receita; retorna null se não encontrado
  public Produto recuperaProdutoPorId(long id) {
    String sql = "SELECT p.id, p.descricao, p.preco, pr.receita_id " +
           "FROM produtos p " +
           "JOIN produto_receita pr ON p.id = pr.produto_id " +
           "WHERE p.id = ?";
    List<Produto> produtos = this.jdbcTemplate.query(
      sql,
      ps -> ps.setLong(1, id),
      (rs, rowNum) -> {
        long produtoId = rs.getLong("id");
        String descricao = rs.getString("descricao");
        int preco = rs.getInt("preco");
        long receitaId = rs.getLong("receita_id");
        Receita receita = receitasRepository.recuperaReceita(receitaId);
        return new Produto(produtoId, descricao, receita, preco);
      }
    );
    return produtos.isEmpty() ? null : produtos.getFirst();    
  }
  
}
