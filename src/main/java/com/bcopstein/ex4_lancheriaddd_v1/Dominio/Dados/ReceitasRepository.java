package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Receita;

//Interface de repositório de receitas; define operações de acesso a dados de receita
public interface ReceitasRepository {
  //Recupera uma receita pelo seu ID, incluindo os ingredientes associados
  Receita recuperaReceita(long id);
}
