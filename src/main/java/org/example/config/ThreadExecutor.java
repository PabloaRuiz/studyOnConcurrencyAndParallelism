package org.example.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface ThreadExecutor {

    Future<Map<Integer, Integer>> submit(org.example.domain.DijkstraImpl dijkstra, Map<Integer, List<org.example.domain.Edge>> edges);
    CompletableFuture<Map<Integer, Integer>> submitAsync(org.example.domain.DijkstraImpl dijkstra, Map<Integer, List<org.example.domain.Edge>> edges);
    void shutdown();
}