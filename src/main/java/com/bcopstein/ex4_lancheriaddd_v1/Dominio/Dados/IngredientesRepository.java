package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;
import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;

//Interface de repositório de ingredientes; define operações de acesso a dados de ingrediente
public interface IngredientesRepository {
  //Retorna a lista de todos os ingredientes cadastrados
  List<Ingrediente> recuperaTodos();

  //Retorna os ingredientes associados a uma receita pelo ID da receita
  List<Ingrediente> recuperaIngredientesReceita(long id);
}
