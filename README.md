# TrabalhoProjarq

Repositório: https://github.com/mellorafaa/TrabalhoProjarq.git

Integrantes:
- Gustavi Trevisol
- Lucas Afonso
- Matheus Corbellini
- Rafaela Mello

---

## Arquitetura

Backend de uma tele-pizza implementado como microsserviços Spring Boot + Spring Cloud:

| Componente         | Porta interna | Descrição                                                          |
|--------------------|---------------|--------------------------------------------------------------------|
| `eureka-server`    | 8761          | Name server (singleton)                                            |
| `gateway`          | 8080          | API Gateway com filtro JWT e load balancer (singleton)             |
| `pizzaria-service` | 8081          | Monólito (escalável)                                               |
| `ms-estoque`       | 8082          | Microsserviço de estoque com JPA (escalável)                       |
| `ms-entregas`      | 8083          | Microsserviço de entregas, consumer RabbitMQ (escalável, ≥ 3)      |
| `rabbitmq`         | 5672 / 15672  | Broker de mensagens (singleton)                                    |

O acesso externo é feito **somente** através do gateway em `http://localhost:8080`.
As réplicas dos microsserviços não expõem porta no host — quem distribui
a carga é o Spring Cloud LoadBalancer (no gateway e no monólito) consultando
o Eureka.

---

## Subindo o ambiente com múltiplas instâncias

### Opção A — Scripts auxiliares

```powershell
# Windows / PowerShell
./scale-up.ps1                                # padrão: 2/2/3
./scale-up.ps1 -Pizzaria 3 -Estoque 2 -Entregas 3 -Rebuild
```

```bash
# Linux / macOS
./scale-up.sh                                 # padrão: 2/2/3
PIZZARIA=3 ESTOQUE=2 ENTREGAS=3 REBUILD=1 ./scale-up.sh
```

### Opção B — Docker Compose direto

```bash
docker compose up -d --build \
  --scale pizzaria-service=2 \
  --scale ms-estoque=2 \
  --scale ms-entregas=3
```

> Conforme o enunciado, o `ms-entregas` precisa de **no mínimo 3 instâncias**
> consumindo da mesma fila (`entregas.pedidos.pagos`).

Verifique no Eureka que as réplicas se registraram:

- http://localhost:8761

Verifique na UI do RabbitMQ os consumidores conectados à fila
`entregas.pedidos.pagos`:

- http://localhost:15672 (guest/guest) → Queues

---

## Como funciona o load balancer

Existem **dois pontos** onde o load balancer atua, ambos via Spring Cloud
LoadBalancer + Eureka:

1. **No gateway** (server-side load balancing externo): cada rota usa
   `lb://<service-id>` em vez de URL fixa. Quando o gateway recebe
   `GET /api/v1/estoque/listar`, ele consulta o Eureka e seleciona uma
   das N instâncias de `ms-estoque` por round-robin.

2. **No monólito** (client-side load balancing interno): o
   `RestTemplate` está anotado com `@LoadBalanced`, então a chamada
   interna `restTemplate.postForEntity("http://ms-estoque/api/v1/...")`
   é interceptada e direcionada para uma das instâncias descobertas.

3. **No RabbitMQ** (competing consumers): as N réplicas do `ms-entregas`
   consomem da mesma fila durável — a primeira que pegar a mensagem
   processa e ela é removida da fila, garantindo distribuição 1-de-N.

---

## Validando o load balancer

Cada microsserviço expõe um endpoint de diagnóstico que devolve o
hostname/porta da instância que respondeu e incrementa um contador
local. Use o script abaixo para disparar várias requisições e ver a
distribuição:

```powershell
# Monólito (pizzaria-service) via gateway
./test-loadbalancer.ps1 -Endpoint http://localhost:8080/instancia -Requisicoes 20

# ms-estoque via gateway
./test-loadbalancer.ps1 -Endpoint http://localhost:8080/api/v1/estoque/instancia -Requisicoes 20

# ms-entregas via gateway
./test-loadbalancer.ps1 -Endpoint http://localhost:8080/api/v1/entregas/instancia -Requisicoes 20
```

Saída esperada: as requisições devem se dividir entre as réplicas
(ex.: 10 para uma e 10 para a outra com 2 instâncias).

Para ver a distribuição de mensagens entre instâncias do `ms-entregas`,
basta acompanhar os logs:

```bash
docker compose logs -f ms-entregas
```

Cada instância imprime `Recebido pedido pago para entrega: pedidoId=...`
apenas para as mensagens que **ela** consumiu.
