package org.example.config;


import org.example.domain.DijkstraImpl;
import org.example.domain.Edge;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FixedThreadPool implements ThreadExecutor {

    ExecutorService pool = Executors.newFixedThreadPool(2);

    @Override
    public Future<Map<Integer, Integer>> submit(DijkstraImpl dijkstra, Map<Integer, List<Edge>> edges) {
        return pool.submit(() -> dijkstra.planning(0, edges));
    }

    @Override
    public CompletableFuture<Map<Integer, Integer>> submitAsync(DijkstraImpl dijkstra, Map<Integer, List<Edge>> edges) {
        return dijkstra.planningAsync(0, edges);
    }

    @Override
    public void shutdown() {
        pool.shutdown();
        try {
            if (pool.awaitTermination(120, SECONDS)) ;
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }
}
