# 📦 SQS Lab

### Uma oficina de pedidos para aprender mensageria na prática.

O **SQS Lab** é um laboratório incremental de processamento de pedidos com Java e Spring. A ideia é começar com uma API simples e evoluí-la, etapa por etapa, para explorar filas, resiliência e arquitetura orientada a eventos — sem esconder a mecânica por trás da mágica.

> **Status: protótipo em construção.** A API de submissão está implementada, mas a integração com SQS ainda é simulada. O destino planejado é o **LocalStack 3.8**, executado localmente — este projeto não pretende publicar em uma conta AWS. Veja [o que funciona hoje](#-estado-atual) antes de esperar uma mensagem na fila.

## 🧭 A jornada do pedido

```text
Cliente ── POST /orders/submit ──▶ API REST
                                      │
                                      ▼
                              Validação da entrada
                                      │
                                      ▼
                              Caso de uso da aplicação
                                      │
                                      ▼
                          Publicador (simulado por enquanto)
```

O projeto segue uma abordagem de **Arquitetura Hexagonal**: a API entra por um adaptador REST, a regra de aplicação coordena a submissão e uma porta de saída separa o caso de uso do mecanismo de publicação.

## ✨ Estado atual

| Etapa                                | Situação                                     |
| ------------------------------------ | -------------------------------------------- |
| Receber pedidos por REST             | ✅ Implementado                              |
| Validar itens e valor informado      | ✅ Implementado                              |
| Gerar um identificador para o pedido | ✅ Implementado                              |
| Aceitar a submissão com HTTP `202`   | ✅ Implementado                              |
| Publicar no SQS via LocalStack 3.8   | 🚧 Simulado; ainda não conectado             |
| Consumir e processar pedidos         | 🗺️ Próxima etapa                             |
| Calcular o total no backend          | 🗺️ Planejado; hoje o valor vem na requisição |

## 🚀 Comece por aqui

### Pré-requisitos

- Java 21
- Acesso à internet na primeira execução para o Maven Wrapper baixar dependências

### Executar a aplicação

O servidor lê a porta da variável `PORT`. Na raiz do projeto:

```bash
PORT=8080 ./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080/sqs-lab`.

### Enviar um pedido

```bash
curl -i -X POST \
  http://localhost:8080/sqs-lab/orders/submit \
  -H 'Content-Type: application/json' \
  -d '{
    "items": ["Air Fryer", "Playstation 5"],
    "amount": 1999.99
  }'
```

Uma submissão válida retorna **`202 Accepted`**, sem corpo de resposta. O campo `items` precisa conter ao menos um item e `amount` precisa ser positivo. O identificador do pedido é criado internamente; ele não é incluído na resposta atual.

> Neste estágio, o adaptador de saída apenas registra a publicação nos logs. Um `202 Accepted` confirma que a API aceitou a solicitação — não que ela já foi persistida ou processada por uma fila real.

### Executar os testes

```bash
./mvnw test
```

## 🧱 Como o código se organiza

```text
src/main/java/dev/soares/sqs_lab/
├── domain/model/                 # Modelo de pedido
├── application/
│   ├── port/in/                  # Entrada do caso de uso
│   ├── port/out/                 # Contrato para publicar pedidos
│   └── usecase/                  # Orquestração da submissão
└── infrastructure/adapter/
    ├── in/rest/                  # Endpoint HTTP e DTO de entrada
    └── out/sqs/                  # Adaptador de publicação (simulado)
```

O endpoint é `POST /sqs-lab/orders/submit`: `/sqs-lab` é o context path da aplicação e `/orders/submit` é a rota REST.

## 🗺️ Próximas missões

As etapas abaixo são objetivos de estudo, não funcionalidades já disponíveis:

1. **Dead Letter Queue** — isolar mensagens que não podem ser processadas.
2. **Retries** — repetir falhas transitórias com uma política explícita.
3. **Visibility Timeout** — entender a janela de processamento e evitar redelivery prematuro.
4. **Idempotência** — impedir que uma mensagem repetida produza efeitos duplicados.
5. **Concorrência** — processar mais de um pedido com segurança.
6. **SNS + SQS Fan-out** — distribuir eventos para múltiplos consumidores.
7. **SQS FIFO** — explorar ordenação e deduplicação.

## 🛠️ Stack

| Tecnologia                            | Papel                                                      |
| ------------------------------------- | ---------------------------------------------------------- |
| Java 21                               | Linguagem                                                  |
| Spring Boot 4.1.1                     | Aplicação e API REST                                       |
| Spring Cloud 2025.1.3                 | Base para integrações Spring Cloud                         |
| Maven Wrapper                         | Build e execução                                           |
| LocalStack 3.8 (SQS) e Docker Compose | Execução local planejada; sem dependência de uma conta AWS |
| Arquitetura Hexagonal                 | Organização do domínio e das integrações                   |

## 🧪 Coleção de requisições

Há uma coleção Bruno em [`collections/SQS Lab`](./collections/SQS%20Lab) com uma requisição de exemplo para submeter um pedido.
