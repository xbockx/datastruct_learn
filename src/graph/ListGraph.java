package graph;

import sun.awt.image.ImageWatched;

import java.util.*;

/**
 * @Description
 * @Author xbockx
 * @Date 1/17/2022
 */
public class ListGraph<V, E> implements Graph<V, E> {

    public static final String RECURSION = "RECURSION";
    public static final String NON_RECURSION = "NON_RECURSION";

    private Map<V, Vertex<V, E>> vertices = new HashMap<>();

    private Set<Edge<V, E>> edges = new HashSet<>();

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int vertexSize() {
        return vertices.size();
    }

    @Override
    public void addVertex(V value) {
        if (vertices.containsKey(value)) {
            return;
        }
        vertices.put(value, new Vertex<>(value));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from, fromVertex);
        }
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;

        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);

    }

    @Override
    public void removeVertex(V value) {
        Vertex<V, E> vertex = vertices.remove(value);
        if (vertex == null) {
            return;
        }

        for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, E> edge = iterator.next();
            edge.to.inEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            return;
        }
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            return;
        }
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    public void print() {
        for (Edge<V, E> edge : edges) {
            System.out.println(edge);
        }
    }

    @Override
    public void bfs(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) {
            return;
        }
        Set<Vertex<V, E>> visited = new HashSet<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        queue.offer(beginVertex);
        visited.add(beginVertex);
        while(!queue.isEmpty()) {
            final Vertex<V, E> vertex = queue.poll();
            System.out.println(vertex);
            for (Edge<V, E> edge : vertex.outEdges) {
                if (!visited.contains(edge.to)) {
                    queue.offer(edge.to);
                    visited.add(edge.to);
                }
            }
        }
    }

    @Override
    public void dfs(V begin) {
        dfs(begin, RECURSION);
    }

    public void dfs(V begin, String method) {
        if (method.equals(RECURSION)) {
            final Vertex<V, E> beginVertex = vertices.get(begin);
            if (beginVertex == null) {
                return;
            }
            dfsRecursion(beginVertex, new HashSet<>());
        } else {
            dfsNonRecursion(begin);
        }
    }

    public void dfsNonRecursion(V begin) {
        final Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) {
            return;
        }

        Set<Vertex<V, E>> visited = new HashSet<>();
        Stack<Vertex<V, E>> stack = new Stack<>();

        // init
        stack.push(beginVertex);
        visited.add(beginVertex);
        // visit
        System.out.println(beginVertex);

        while(!stack.isEmpty()) {
            final Vertex<V, E> vertex = stack.pop();
            for (Edge<V, E> edge : vertex.outEdges) {
                if (visited.contains(edge.to)) {
                    continue;
                }
                stack.push(edge.from);
                stack.push(edge.to);
                visited.add(edge.to);
                System.out.println(edge.to);
                break;
            }
        }
    }

    private void dfsRecursion(Vertex<V, E> vertex, Set<Vertex<V, E>> visited) {
        System.out.println(vertex);
        visited.add(vertex);

        for (Edge<V, E> edge : vertex.outEdges) {
            if (visited.contains(edge.to)) {
                continue;
            }
            dfsRecursion(edge.to, visited);
        }
    }

    private static class Vertex<V, E> {
        V value;
        Set<Edge<V, E>> inEdges = new HashSet<>();
        Set<Edge<V, E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "value=" + value +
                    '}';
        }
    }

    private static class Edge<V, E> {
        Vertex<V, E> from;
        Vertex<V, E> to;
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?, ?> edge = (Edge<?, ?>) o;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
