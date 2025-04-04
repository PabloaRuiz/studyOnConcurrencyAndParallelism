package org.example.domain;

public class Edge {

    private int node;
    private int weight;

    public int getNode() {
        return node;
    }

    public Edge() {
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Edge(int to, int weight) {
        this.node = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node=" + node +
                ", weight=" + weight +
                '}';
    }
}
