package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
// Classe ClienteResponse: responsabilidade principal inferida pelo nome 

//Resposta com os dados resumidos de um cliente para uso na camada de aplicação
public record ClienteResponse(String cpf, String nome, String celular, String endereco, String email) {}
