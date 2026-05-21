package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

public class PagarPedidoResponse {

    private final boolean pago;
    private final String mensagem;
    private final long idPedido;
    private final String status;

    public PagarPedidoResponse(boolean pago, String mensagem, long idPedido, String status) {
        this.pago     = pago;
        this.mensagem = mensagem;
        this.idPedido = idPedido;
        this.status   = status;
    }

    public boolean isPago()       { return pago; }
    public String getMensagem()   { return mensagem; }
    public long getIdPedido()     { return idPedido; }
    public String getStatus()     { return status; }
}
