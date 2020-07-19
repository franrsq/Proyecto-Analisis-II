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
        Graph grafo = new Graph();
        grafo.createGraph(10);
        DijkstraAlgorithm disjkstra = new DijkstraAlgorithm(grafo);
        disjkstra.execute(grafo.firstVertex);
        LinkedList<Vertex> path = disjkstra.getPath(grafo.lastVertex);
        for (Vertex vertex : path) {
            System.out.println(vertex.getNumber());
        }
    }

}
