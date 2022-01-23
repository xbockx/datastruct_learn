package graph;

/**
 * @Description
 * @Author xbockx
 * @Date 1/23/2022
 */
public class Main {
    public static void main(String[] args) {
        Graph<String, Integer> graph = new ListGraph();
        graph.addEdge("V0", "V1", 1);
        graph.addEdge("V1", "V2", 2);
        graph.addEdge("V2", "V0", 3);
        graph.print();
    }
}
