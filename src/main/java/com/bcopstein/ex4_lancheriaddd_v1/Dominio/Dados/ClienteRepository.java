package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;
// Classe ClienteRepository: responsabilidade principal inferida pelo nome 

import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

//Interface de repositório de clientes; define operações de acesso a dados de cliente
public interface ClienteRepository {

  //Busca um cliente pelo CPF; retorna null se não encontrado
  Cliente recuperarPorCpf(String cpf);

  //Busca um cliente pelo e-mail; retorna null se não encontrado
  Cliente recuperarPorEmail(String email);

  //Retorna a lista de todos os clientes cadastrados
  List<Cliente> recuperarTodos();

  //Persiste um novo cliente e retorna o objeto salvo
  Cliente salvar(Cliente cliente);
}
