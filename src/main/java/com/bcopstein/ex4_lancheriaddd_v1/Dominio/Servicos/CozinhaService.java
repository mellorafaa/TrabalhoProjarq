package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class CozinhaService implements ICozinhaService {

    private final PedidoRepository pedidoRepository;
    private final IEntregaService entregaService;

    private final Queue<Pedido> filaEntrada;
    private Pedido emPreparacao;
    private final ScheduledExecutorService scheduler;

    @Autowired
    public CozinhaService(PedidoRepository pedidoRepository, IEntregaService entregaService) {
        this.pedidoRepository = pedidoRepository;
        this.entregaService   = entregaService;
        this.filaEntrada      = new LinkedBlockingQueue<>();
        this.emPreparacao     = null;
        this.scheduler        = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public synchronized void chegadaDePedido(Pedido p) {
        p.setStatus(Pedido.Status.AGUARDANDO);
        pedidoRepository.atualizarStatus(p.getId(), Pedido.Status.AGUARDANDO);
        filaEntrada.add(p);
        System.out.println("Pedido na fila de entrada da cozinha: " + p.getId());
        if (emPreparacao == null) {
            colocaEmPreparacao(filaEntrada.poll());
        }
    }

    private synchronized void colocaEmPreparacao(Pedido pedido) {
        pedido.setStatus(Pedido.Status.PREPARACAO);
        pedidoRepository.atualizarStatus(pedido.getId(), Pedido.Status.PREPARACAO);
        emPreparacao = pedido;
        System.out.println("Pedido em preparacao: " + pedido.getId());
        scheduler.schedule(() -> pedidoPronto(), 5, TimeUnit.SECONDS);
    }

    @Override
    public synchronized void pedidoPronto() {
        if (emPreparacao == null) {
            return;
        }
        emPreparacao.setStatus(Pedido.Status.PRONTO);
        pedidoRepository.atualizarStatus(emPreparacao.getId(), Pedido.Status.PRONTO);
        System.out.println("Pedido pronto: " + emPreparacao.getId());

        Pedido pronto = emPreparacao;
        emPreparacao = null;

        entregaService.chegadaDePedido(pronto);

        if (!filaEntrada.isEmpty()) {
            Pedido prox = filaEntrada.poll();
            scheduler.schedule(() -> colocaEmPreparacao(prox), 1, TimeUnit.SECONDS);
        }
    }
}
