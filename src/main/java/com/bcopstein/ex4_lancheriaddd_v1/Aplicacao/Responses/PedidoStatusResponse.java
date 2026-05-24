package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
// Classe PedidoStatusResponse: responsabilidade principal inferida pelo nome 

//Resposta contendo o ID e o status atual de um pedido
public record PedidoStatusResponse(long id, String status) {}
