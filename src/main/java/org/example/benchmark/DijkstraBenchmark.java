package org.example.benchmark;

import org.example.config.FixedThreadPool;
import org.example.config.VirtualThreads;
import org.example.domain.DijkstraImpl;
import org.example.domain.Edge;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

@State(Scope.Benchmark)
@BenchmarkMode(AverageTime)
@OutputTimeUnit(MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class DijkstraBenchmark {

    private DijkstraImpl dijkstra;
    private List<Map<Integer, List<Edge>>> graphs;
    private FixedThreadPool fixedThreadPool;
    private VirtualThreads virtualThreads;

    @Setup
    public void setup() {
        dijkstra = new DijkstraImpl();
        graphs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            graphs.add(dijkstra.createEdges(1000, 500));
        }
        fixedThreadPool = new FixedThreadPool();
        virtualThreads = new VirtualThreads();
    }

    @TearDown
    public void tearDown() {
        fixedThreadPool.shutdown();
        virtualThreads.shutdown();
    }

    @Benchmark
    public void testFixedThreadPool(Blackhole blackhole) throws Exception {
        List<Future<Map<Integer, Integer>>> futures = new ArrayList<>();
        for (Map<Integer, List<Edge>> graph : graphs) {
            futures.add(fixedThreadPool.submit(dijkstra, graph));
        }
        for (Future<Map<Integer, Integer>> future : futures) {
            blackhole.consume(future.get());
        }
    }

    @Benchmark
    public void testFixedThreadPoolAsync(Blackhole blackhole) throws Exception {
        List<CompletableFuture<Map<Integer, Integer>>> futures = new ArrayList<>();
        for (Map<Integer, List<Edge>> graph : graphs) {
            futures.add(fixedThreadPool.submitAsync(dijkstra, graph));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        for (CompletableFuture<Map<Integer, Integer>> future : futures) {
            blackhole.consume(future.get());
        }
    }

    @Benchmark
    public void testVirtualThreads(Blackhole blackhole) throws Exception {
        List<CompletableFuture<Map<Integer, Integer>>> futures = new ArrayList<>();
        for (Map<Integer, List<Edge>> graph : graphs) {
            futures.add(virtualThreads.submitAsync(dijkstra, graph));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        for (CompletableFuture<Map<Integer, Integer>> future : futures) {
            blackhole.consume(future.get());
        }
    }


    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}