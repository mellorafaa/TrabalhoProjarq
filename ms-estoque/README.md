# Microsserviço de Estoque (ms-estoque)

## 📋 Descrição

Microsserviço responsável por gerenciar o inventário de ingredientes da pizzaria. Implementa verificação de disponibilidade e atualização de estoque com base em pedidos, usando comunicação assíncrona via RabbitMQ.

## 🏗️ Arquitetura

```
Dominio/
├── Entidades/
│   ├── Ingrediente.java       - Entidade: ingredientes disponíveis
│   └── ItemEstoque.java       - Entidade: estoque de ingredientes
└── Servicos/
    └── IEstoqueService.java   - Interface de serviço de estoque

Aplicacao/
├── VerificarEstoqueUC.java    - Use Case: verifica disponibilidade
└── AtualizarEstoqueUC.java    - Use Case: atualiza quantidades

Adaptadores/
├── Apresentacao/
│   ├── EstoqueController.java - REST API
│   └── Dtos/                  - Data Transfer Objects
├── Dados/
│   ├── EstoqueService.java    - Implementação de serviço (JPA)
│   ├── IngredienteRepository.java   - Repositório JPA
│   └── ItemEstoqueRepository.java   - Repositório JPA
└── Mensageria/
    ├── RabbitMQConfig.java    - Configuração de filas
    ├── PedidosConsumer.java   - Consome eventos de pedidos
    ├── EstoqueProducer.java   - Publica validações
    └── Dtos/                  - Event DTOs
```

## 🔄 Fluxo de Mensageria

### 1. Recebimento de Pedido
```
ms-pedidos → [Fila: pedidos.criados] → ms-estoque (PedidosConsumer)
                                        ↓
                                        Verifica estoque
                                        ↓
ms-pedidos ← [Fila: estoque.validado] ← ms-estoque (EstoqueProducer)
```

### 2. Confirmação de Estoque
```
PedidosConsumer
  ├─ Recebe PedidoEventDTO da fila "pedidos.criados"
  ├─ Converte itens para ItemEstoqueDTO
  ├─ Executa VerificarEstoqueUC
  └─ Envia resposta:
      ├─ ✅ Se tudo ok: EstoqueProducer.enviarEstoqueValidado()
      └─ ❌ Se erro: EstoqueProducer.enviarEstoqueInvalido()
```

### 3. Atualização de Estoque
```
ms-pedidos → [Fila: estoque.atualizar] → ms-estoque (PedidosConsumer)
                                          ↓
                                          AtualizarEstoqueUC
                                          ↓
                                          Reduz quantidades
```

## 📡 Endpoints REST

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/estoque/verificar` | Verifica disponibilidade de itens |
| POST | `/api/v1/estoque/atualizar` | Atualiza estoque (reduz quantidades) |
| GET | `/api/v1/estoque/listar` | Lista todos os itens em estoque |
| GET | `/api/v1/estoque/ingrediente/{id}` | Obtém item por ID de ingrediente |

## 🐰 Filas RabbitMQ

### Consumer (Recebe)
- **Fila**: `estoque.pedidos.criados`
  - **Exchange**: `pedidos.exchange`
  - **Routing Key**: `pedidos.criar`
  - **Payload**: `PedidoEventDTO`

- **Fila**: `estoque.atualizar`
  - **Exchange**: `pedidos.exchange`
  - **Routing Key**: `estoque.atualizar`
  - **Payload**: `PedidoEventDTO`

### Producer (Envia)
- **Fila**: `estoque.validado`
  - **Exchange**: `estoque.exchange`
  - **Routing Keys**: 
    - `estoque.validado.ok` (sucesso)
    - `estoque.validado.erro` (erro)
  - **Payload**: `EstoqueValidacaoEventDTO`

## 🗄️ Banco de Dados

Utiliza **H2 Database** em memória com dados iniciais pré-carregados.

### Tabelas
- **ingredientes**: lista de ingredientes disponíveis
- **itens_estoque**: quantidade em estoque de cada ingrediente

### Dados Iniciais
- 10 ingredientes (Massa, Molho, Queijo, Calabresa, etc.)
- Estoque inicial para cada ingrediente
- Quantidade mínima para reposição

## 🚀 Como Executar

### Localmente
```bash
cd ms-estoque
mvn clean install
mvn spring-boot:run
```

### Via Docker (com docker-compose)
```bash
cd ..
docker-compose up -d ms-estoque
```

### Acessar
- **API**: `http://localhost:8082/api/v1/estoque`
- **H2 Console**: `http://localhost:8082/h2-console`
  - JDBC URL: `jdbc:h2:mem:estoquedb`
  - User: `sa`
  - Password: (vazio)

## 📦 Dependências Principais

- Spring Boot 3.4.3
- Spring Data JPA (com Hibernate)
- Spring AMQP (RabbitMQ)
- H2 Database
- Lombok
- Eureka Client

## 🔧 Configuração (application.yml)

```yaml
spring:
  application:
    name: ms-estoque
  datasource:
    url: jdbc:h2:mem:estoquedb
  rabbitmq:
    host: rabbitmq
    port: 5672
    username: guest
    password: guest

eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka-server:8761/eureka/

server:
  port: 8082
  servlet:
    context-path: /api/v1
```

## 📝 Exemplo de Uso

### 1. Verificar Estoque
```bash
curl -X POST http://localhost:8082/api/v1/estoque/verificar \
  -H "Content-Type: application/json" \
  -d '[
    {"ingredienteId": 1, "quantidade": 5},
    {"ingredienteId": 3, "quantidade": 10}
  ]'
```

**Resposta (200 - OK)**:
```json
[]  # Lista vazia = tudo disponível
```

### 2. Atualizar Estoque
```bash
curl -X POST http://localhost:8082/api/v1/estoque/atualizar \
  -H "Content-Type: application/json" \
  -d '[
    {"ingredienteId": 1, "quantidade": 5},
    {"ingredienteId": 3, "quantidade": 2}
  ]'
```

**Resposta**:
```json
"Estoque atualizado com sucesso"
```

### 3. Listar Estoque
```bash
curl http://localhost:8082/api/v1/estoque/listar
```

## 🔌 Integração com ms-pedidos

O ms-estoque se integra com ms-pedidos através de mensagens RabbitMQ:

1. **ms-pedidos** cria um pedido e publica em `pedidos.criados`
2. **ms-estoque** recebe e valida o estoque
3. **ms-estoque** publica resultado em `estoque.validado`
4. **ms-pedidos** recebe a validação e atualiza o pedido
5. Se aprovado, **ms-pedidos** publica em `estoque.atualizar`
6. **ms-estoque** atualiza as quantidades

## 📊 Próximos Passos

- [ ] Testes unitários
- [ ] Testes de integração
- [ ] Validação de entrada
- [ ] Logs estruturados
- [ ] Métricas e monitoramento
- [ ] Autenticação/Autorização
- [ ] Paginação nos endpoints de listagem

---

**Desenvolvido com ❤️ para Projarc**
