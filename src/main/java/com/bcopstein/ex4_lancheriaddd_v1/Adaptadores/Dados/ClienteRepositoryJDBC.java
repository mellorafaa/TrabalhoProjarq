package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;
// Implementação JDBC do repositório de clientes; persiste e consulta registros na tabela clientes

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

@Component
public class ClienteRepositoryJDBC implements ClienteRepository {

  private final JdbcTemplate jdbcTemplate;

  public ClienteRepositoryJDBC(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  // Busca um cliente pelo CPF na tabela clientes; retorna null se não encontrado
  public Cliente recuperarPorCpf(String cpf) {
    String sql = "SELECT cpf, nome, celular, endereco, email, senha FROM clientes WHERE cpf = ?";

    List<Cliente> clientes = jdbcTemplate.query(
        sql,
        ps -> ps.setString(1, cpf),
        (rs, rowNum) -> new Cliente(
            rs.getString("cpf"),
            rs.getString("nome"),
            rs.getString("celular"),
            rs.getString("endereco"),
            rs.getString("email"),
            rs.getString("senha")
        )
    );

    return clientes.isEmpty() ? null : clientes.get(0);
  }

  @Override
  // Busca um cliente pelo e-mail na tabela clientes; retorna null se não encontrado
  public Cliente recuperarPorEmail(String email) {
    String sql = "SELECT cpf, nome, celular, endereco, email, senha FROM clientes WHERE email = ?";

    List<Cliente> clientes = jdbcTemplate.query(
        sql,
        ps -> ps.setString(1, email),
        (rs, rowNum) -> new Cliente(
            rs.getString("cpf"),
            rs.getString("nome"),
            rs.getString("celular"),
            rs.getString("endereco"),
            rs.getString("email"),
            rs.getString("senha")
        )
    );

    return clientes.isEmpty() ? null : clientes.get(0);
  }

  @Override
  // Retorna todos os clientes cadastrados na tabela clientes
  public List<Cliente> recuperarTodos() {
    String sql = "SELECT cpf, nome, celular, endereco, email, senha FROM clientes";

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> new Cliente(
            rs.getString("cpf"),
            rs.getString("nome"),
            rs.getString("celular"),
            rs.getString("endereco"),
            rs.getString("email"),
            rs.getString("senha")
        )
    );
  }

  @Override
  // Insere um novo cliente na tabela clientes e retorna o objeto persistido
  public Cliente salvar(Cliente cliente) {
    String sql = "INSERT INTO clientes (cpf, nome, celular, endereco, email, senha) VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(
        sql,
        cliente.getCpf(),
        cliente.getNome(),
        cliente.getCelular(),
        cliente.getEndereco(),
        cliente.getEmail(),
        cliente.getSenhaHash()
    );
    return cliente;
  }
}
