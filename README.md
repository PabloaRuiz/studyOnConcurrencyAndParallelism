# Concurrent Seat Reservation System (CompletableFuture & Threading)

[![codecov](https://codecov.io/gh/PabloaRuiz/studyOnConcurrencyAndParallelism/branch/main/graph/badge.svg)](https://codecov.io/gh/PabloaRuiz/studyOnConcurrencyAndParallelism)
![GitHub tag (latest)](https://img.shields.io/github/v/tag/PabloaRuiz/studyOnConcurrencyAndParallelism)
![Build](https://github.com/PabloaRuiz/studyOnConcurrencyAndParallelism/actions/workflows/challenge-Workflow.yml/badge.svg)

Este projeto simula um sistema de **reserva de assentos** usando Java moderno e técnicas avançadas de **concorrência com `CompletableFuture`**, e realizar testes de benchmarks reais de performance com **JMH**.

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
