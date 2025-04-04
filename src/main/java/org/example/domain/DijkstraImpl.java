package org.example.domain;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static java.lang.Integer.MAX_VALUE;

public class DijkstraImpl implements Dijkstra {

    @Override
    public Map<Integer, Integer> planning(int start, Map<Integer, List<Edge>> graph) {
        return graphPlanning(start, graph);
    }

    @Override
    public CompletableFuture<Map<Integer, Integer>> planningAsync(int start, Map<Integer, List<Edge>> graph) {
        return CompletableFuture.supplyAsync(() -> graphPlanning(start, graph));
    }

    private Map<Integer, Integer> graphPlanning(int start, Map<Integer, List<Edge>> graph) {
        var begin = System.currentTimeMillis();
        System.out.printf("Thread: %-20s | Início: %d%n", Thread.currentThread().getName(), begin);
        Map<Integer, Integer> dist = new HashMap<>();
        PriorityQueue<int[]> priority = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        for (Integer node : graph.keySet()) {
            dist.put(node, MAX_VALUE);
        }

        dist.put(start, 0);
        priority.offer(new int[]{start, 0});

        while (!priority.isEmpty()) {
            int[] current = priority.poll();
            int node = current[0];
            int currDistance = current[1];


            if (currDistance > dist.get(node)) continue;

            for (Edge edge : graph.getOrDefault(node, new ArrayList<>())) {
                int newDist = currDistance + edge.getWeight();
                if (newDist < dist.get(edge.getNode())) {
                    dist.put(edge.getNode(), newDist);
                    priority.offer(new int[]{edge.getNode(), newDist});
                }

            }
        }

        var end = System.currentTimeMillis();
        var duration = end - begin;
        System.out.printf("Thread: %-20s | Fim: %d  %nDuração: %d milli %n",
                Thread.currentThread().getName(), end, duration);
        return dist;
    }

    public Map<Integer, List<Edge>> createEdges(int size, int edges) {
        Map<Integer, List<Edge>> graph = new HashMap<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < edges; j++) {
                int key = random.nextInt(size);
                int node = random.nextInt(size);
                int weight = random.nextInt(100) + 1;
                graph.computeIfAbsent(key, k -> new ArrayList<>()).add(new Edge(node, weight));
            }
        }

        return graph;
    }
}
