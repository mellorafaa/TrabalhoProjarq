
package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;
// Implementação JDBC do repositório de receitas; consulta receitas e seus ingredientes no banco de dados

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Receita;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IngredientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ReceitasRepository;

@Repository
public class ReceitasRepositoryJDBC implements ReceitasRepository {
  private final JdbcTemplate jdbcTemplate;
  private final IngredientesRepository ingredientesRepository;

  public ReceitasRepositoryJDBC(JdbcTemplate jdbcTemplate, IngredientesRepository ingredientesRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.ingredientesRepository = ingredientesRepository;
  }

  @Override
  // Busca uma receita pelo ID carregando os ingredientes associados; retorna null se não encontrada
  public Receita recuperaReceita(long id) {
    String sql = "SELECT r.id, r.titulo FROM receitas r WHERE r.id = ?";
    List<Receita> receitas = this.jdbcTemplate.query(
      sql,
      ps -> ps.setLong(1, id),
      (rs, rowNum) -> {
        long receitaId = rs.getLong("id");
        String titulo = rs.getString("titulo");
        List<Ingrediente> ingredientes = ingredientesRepository.recuperaIngredientesReceita(receitaId);
        return new Receita(receitaId, titulo, ingredientes); 
      }
    );
    return receitas.isEmpty() ? null : receitas.get(0);
  }

}


