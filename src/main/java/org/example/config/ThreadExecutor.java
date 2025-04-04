package org.example.config;

import org.example.Threads.GerenciandoThreads.Challenge.domain.DijkstraImpl;
import org.example.Threads.GerenciandoThreads.Challenge.domain.Edge;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface ThreadExecutor {

    Future<Map<Integer, Integer>> submit(DijkstraImpl dijkstra, Map<Integer, List<Edge>> edges);
    CompletableFuture<Map<Integer, Integer>> submitAsync(DijkstraImpl dijkstra, Map<Integer, List<Edge>> edges);
    void shutdown();
}
