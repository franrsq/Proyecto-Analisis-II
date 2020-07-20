package proyectoanalisisii;

import dinamicAlgorithm.DijkstraAlgorithm;
import java.util.LinkedList;
import proyectoanalisisii.graph.Graph;
import proyectoanalisisii.graph.Vertex;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Graph graph = new Graph();
        graph.createGraph(10);
        graph.printGraph();
        graph.voracious();

        DijkstraAlgorithm disjkstra = new DijkstraAlgorithm(graph);
        disjkstra.execute(graph.firstVertex);
        LinkedList<Vertex> path = disjkstra.getPath(graph.lastVertex);
        for (Vertex vertex : path) {
            System.out.println(vertex.getNumber());
        }
    }

}
