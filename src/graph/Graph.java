package graph;

/**
 * @Description
 * @Author xbockx
 * @Date 1/17/2022
 */
public interface Graph<V, E> {

    int edgeSize();

    int vertexSize();

    void addVertex(V value);

    void addEdge(V from, V to);

    void addEdge(V from, V to, E weight);

    void removeVertex(V value);

    void removeEdge(V from, V to);

    void print();

}
