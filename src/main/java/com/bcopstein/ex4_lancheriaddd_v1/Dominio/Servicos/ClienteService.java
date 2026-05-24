package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe ClienteService: responsabilidade principal inferida pelo nome 

import java.util.List;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

//Serviço de domínio responsável pelas regras de negócio de cadastro e consulta de clientes
@Service
public class ClienteService {

  private final ClienteRepository clienteRepository;

  //Injeta o repositório de clientes
  public ClienteService(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  //Valida e persiste um novo cliente, lançando exceção se dados inválidos ou duplicados
  public Cliente cadastrarCliente(Cliente cliente) {
    if (cliente == null) {
      throw new RuntimeException("Cliente inválido.");
    }

    if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
      throw new RuntimeException("CPF é obrigatório.");
    }
    if (cliente.getNome() == null || cliente.getNome().isBlank()) {
      throw new RuntimeException("Nome é obrigatório.");
    }
    if (cliente.getCelular() == null || cliente.getCelular().isBlank()) {
      throw new RuntimeException("Celular é obrigatório.");
    }
    if (cliente.getEndereco() == null || cliente.getEndereco().isBlank()) {
      throw new RuntimeException("Endereço é obrigatório.");
    }
    if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
      throw new RuntimeException("Email é obrigatório.");
    }
    if (cliente.getSenhaHash() == null || cliente.getSenhaHash().isBlank()) {
      throw new RuntimeException("Senha é obrigatória.");
    }

    if (clienteRepository.recuperarPorCpf(cliente.getCpf()) != null) {
      throw new RuntimeException("Já existe um cliente cadastrado com o CPF informado.");
    }

    if (clienteRepository.recuperarPorEmail(cliente.getEmail()) != null) {
      throw new RuntimeException("Já existe um cliente cadastrado com o email informado.");
    }

    return clienteRepository.salvar(cliente);
  }

  //Retorna a lista de todos os clientes cadastrados
  public List<Cliente> listarClientes() {
    return clienteRepository.recuperarTodos();
  }
}
