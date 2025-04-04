# Concurrent Seat Reservation System (CompletableFuture & Threading)

Este projeto simula um sistema de **reserva de assentos** usando Java moderno e técnicas avançadas de **concorrência com `CompletableFuture`**, além de simular envio de e-mail e realizar testes e benchmarks reais de performance com **JMH**.

## 🚀 Objetivo

Explorar e praticar os conceitos de:

- Concorrência com `CompletableFuture`
- Controle de sincronização (`synchronized`, `handle`, `thenCompose`)
- Benchmark de performance (JMH)

---

## 🔧 Tecnologias

- Java 21+
- JMH (Benchmarking)

---

## 📂 Estrutura

```shell
.
├── domain/
│   ├── DijkstraImpl.java
│   ├── Edge.java
│   └── ThreadExecutor.java
│
├── executors/
│   ├── FixedThreadPool.java
│   ├── PoolExecutorThreads.java
│   └── VirtualThreads.java
│
├── benchmark/
│   └── DijkstraBenchmark.java
│
└── Main.java
