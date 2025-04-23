package org;

import org.example.config.FixedThreadPool;
import org.example.config.PoolExecutorThreads;
import org.example.config.VirtualThreads;
import org.example.domain.DijkstraImpl;
import org.example.domain.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DijkstraTest {

    private DijkstraImpl dijkstra;
    private Map<Integer, List<Edge>> smallGraph;

    @BeforeEach
    void setUp() {
        dijkstra = new DijkstraImpl();
        smallGraph = new HashMap<>();
        smallGraph.put(0, Arrays.asList(new Edge(1, 4), new Edge(2, 1)));
        smallGraph.put(1, Collections.singletonList(new Edge(3, 1)));
        smallGraph.put(2, Collections.singletonList(new Edge(3, 5)));
        smallGraph.put(3, Collections.emptyList());
    }


    @Test
    @DisplayName("Teste básico do Dijkstra síncrono")
    void testDijkstraSync() {
        Map<Integer, Integer> result = dijkstra.planning(0, smallGraph);
        assertEquals(0, result.get(0), "Distância do início deve ser 0");
        assertEquals(4, result.get(1), "Distância para o nó 1 deve ser 4");
        assertEquals(1, result.get(2), "Distância para o nó 2 deve ser 1");
        assertEquals(5, result.get(3), "Distância para o nó 3 deve ser 5");
    }

    @Test
    @DisplayName("Teste assíncrono do Dijkstra com CompletableFuture")
    void testDijkstraAsync() throws Exception {
        CompletableFuture<Map<Integer, Integer>> future = dijkstra.planningAsync(0, smallGraph);
        Map<Integer, Integer> result = future.get(5, TimeUnit.SECONDS);
        assertEquals(0, result.get(0));
        assertEquals(4, result.get(1));
        assertEquals(1, result.get(2));
        assertEquals(5, result.get(3));
    }

    @Test
    @DisplayName("Teste concorrente com FixedThreadPool")
    void testFixedThreadPoolConcurrency() throws Exception {
        FixedThreadPool executor = new FixedThreadPool();
        List<Future<Map<Integer, Integer>>> futures = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Map<Integer, List<Edge>> graph = dijkstra.createEdges(100, 50);
            futures.add(executor.submit(dijkstra, graph));
        }

        for (Future<Map<Integer, Integer>> future : futures) {
            Map<Integer, Integer> result = future.get(10, TimeUnit.SECONDS);
            assertNotNull(result);
            assertTrue(result.containsKey(0));
            assertEquals(0, result.get(0), "Nó inicial deve ter distância 0");
        }

        executor.shutdown();
    }

    @Test
    @DisplayName("Teste concorrente com VirtualThreads")
    void testVirtualThreadsConcurrency() throws Exception {
        VirtualThreads executor = new VirtualThreads();
        List<CompletableFuture<Map<Integer, Integer>>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Map<Integer, List<Edge>> graph = dijkstra.createEdges(200, 100);
            futures.add(executor.submitAsync(dijkstra, graph));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        for (CompletableFuture<Map<Integer, Integer>> future : futures) {
            Map<Integer, Integer> result = future.get();
            assertNotNull(result);
            assertEquals(0, result.get(0), "Nó inicial deve ter distância 0");
        }

        executor.shutdown();
    }

    @Test
    @DisplayName("Teste de estresse com PoolExecutorThreads")
    void testPoolExecutorStress() throws Exception {
        PoolExecutorThreads executor = new PoolExecutorThreads();
        List<Future<Map<Integer, Integer>>> futures = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Map<Integer, List<Edge>> graph = dijkstra.createEdges(400, 200);
            futures.add(executor.submit(dijkstra, graph));
        }

        for (Future<Map<Integer, Integer>> future : futures) {
            Map<Integer, Integer> result = future.get(15, TimeUnit.SECONDS);
            assertNotNull(result);
            assertEquals(0, result.get(0));
        }

        executor.shutdown();
    }

    @Test
    @DisplayName("Comparação de desempenho entre executores")
    void testPerformanceComparison() throws Exception {
        DijkstraImpl dijkstra = new DijkstraImpl();
        Map<Integer, List<Edge>> largeGraph = dijkstra.createEdges(1000, 500);

        FixedThreadPool fixed = new FixedThreadPool();
        long start = System.nanoTime();
        fixed.submit(dijkstra, largeGraph).get();
        long fixedTime = System.nanoTime() - start;
        fixed.shutdown();

        VirtualThreads virtual = new VirtualThreads();
        start = System.nanoTime();
        virtual.submitAsync(dijkstra, largeGraph).join();
        long virtualTime = System.nanoTime() - start;
        virtual.shutdown();

        System.out.printf("FixedThreadPool: %d ns%nVirtualThreads: %d ns%n", fixedTime, virtualTime);
    }
}
