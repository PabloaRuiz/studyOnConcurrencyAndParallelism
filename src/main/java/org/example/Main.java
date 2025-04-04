package org.example;

import org.example.Threads.GerenciandoThreads.Challenge.config.FixedThreadPool;
import org.example.Threads.GerenciandoThreads.Challenge.domain.DijkstraImpl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {

        DijkstraImpl dijkstra = new DijkstraImpl();
        var edges = dijkstra.createEdges(400, 100);

        FixedThreadPool fixedThreadPool = new FixedThreadPool();

        Future<Map<Integer, Integer>> futureResultado = fixedThreadPool.submit(dijkstra, edges);

        CompletableFuture<Map<Integer, Integer>> future = fixedThreadPool.submitAsync(dijkstra, edges);

        Map<Integer, Integer> resultado = futureResultado.get();

        var futureResult = future.join();

        fixedThreadPool.shutdown();

        System.out.println("Main recebeu o resultado com " + resultado.size() + " nós.");
        System.out.println("Resultado parcial: " + resultado.entrySet().stream().toList());

        System.out.println("Main recebeu o resultado com " + futureResult.size() + " nós. Pela chamada Async");
        System.out.println("Resultado parcial: " + futureResult.entrySet().stream().toList() + " pela chamada Async");

        System.out.println("Finalizado na main!");
    }
}
