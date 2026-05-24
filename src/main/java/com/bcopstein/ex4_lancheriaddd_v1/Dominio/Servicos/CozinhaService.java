package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
// Classe CozinhaService: responsabilidade principal inferida pelo nome 

import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

//Serviço de domínio que gerencia a fila de preparo dos pedidos na cozinha de forma assíncrona
@Service
public class CozinhaService implements ICozinhaService {

  private static final Logger log = LoggerFactory.getLogger(CozinhaService.class);

  private final PedidoRepository pedidoRepository;
  private final IEntregaService entregaService;

  private final Queue<Pedido> filaEntrada;
  private Pedido emPreparacao;
  private final ScheduledExecutorService scheduler;

  //Inicializa a cozinha com a fila de pedidos e o agendador de tarefas
  public CozinhaService(PedidoRepository pedidoRepository, IEntregaService entregaService) {
    this.pedidoRepository = pedidoRepository;
    this.entregaService  = entregaService;
    this.filaEntrada   = new LinkedBlockingQueue<>();
    this.emPreparacao   = null;
    this.scheduler    = Executors.newSingleThreadScheduledExecutor();
  }

  //Recebe um pedido pago, coloca na fila e inicia o preparo se a cozinha estiver livre
  @Override
  // Método chegadaDePedido: public chegadaDePedido — descrição breve 
  public synchronized void chegadaDePedido(Pedido p) {
    p.iniciarPreparo();
    pedidoRepository.atualizarStatus(p.getId(), Pedido.Status.AGUARDANDO);
    filaEntrada.add(p);
    log.info("Pedido na fila de entrada da cozinha: {}", p.getId());
    if (emPreparacao == null) {
      colocaEmPreparacao(filaEntrada.poll());
    }
  }

  //Inicia a preparação do pedido e agenda a conclusão após 5 segundos
  private synchronized void colocaEmPreparacao(Pedido pedido) {
    pedido.comecarPreparacao();
    pedidoRepository.atualizarStatus(pedido.getId(), Pedido.Status.PREPARACAO);
    emPreparacao = pedido;
    log.info("Pedido em preparacao: {}", pedido.getId());
    scheduler.schedule(() -> pedidoPronto(), 5, TimeUnit.SECONDS);
  }

  //Marca o pedido em preparo como pronto e o encaminha para entrega; inicia o próximo da fila
  @Override
  // Método pedidoPronto: public pedidoPronto — descrição breve 
  public synchronized void pedidoPronto() {
    if (emPreparacao == null) {
      return;
    }
    emPreparacao.marcarPronto();
    pedidoRepository.atualizarStatus(emPreparacao.getId(), Pedido.Status.PRONTO);
    log.info("Pedido pronto: {}", emPreparacao.getId());

    Pedido pronto = emPreparacao;
    emPreparacao = null;

    entregaService.chegadaDePedido(pronto);

    if (!filaEntrada.isEmpty()) {
      Pedido prox = filaEntrada.poll();
      scheduler.schedule(() -> colocaEmPreparacao(prox), 1, TimeUnit.SECONDS);
    }
  }
}
