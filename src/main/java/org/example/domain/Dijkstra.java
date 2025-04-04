package org.example.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Dijkstra {

   Map<Integer, Integer> planning(int start, Map<Integer, List<Edge>> graph);

   CompletableFuture<Map<Integer, Integer>> planningAsync(int start, Map<Integer, List<Edge>> graph);

}
