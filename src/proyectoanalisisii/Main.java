package proyectoanalisisii;

import dinamicAlgorithm.DijkstraAlgorithm;
import geneticAlgorithm.GeneticAlgorithm;
import java.lang.instrument.Instrumentation;
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
     
        //Voraz
        //graph.printGraph();  
        long start = System.currentTimeMillis();
        graph.voracious();
        long finish = System.currentTimeMillis();
        graph.printVars(finish - start);
    
        //graph.printGraph();
        GeneticAlgorithm genetic = new GeneticAlgorithm(graph);
        start = System.currentTimeMillis();
        genetic.execute();
        genetic.printRoute();
        finish = System.currentTimeMillis();
        graph.printVars(finish - start);
        
 
        //Genetico
        
        
        /*
        //Dinamico
        DijkstraAlgorithm disjkstra = new DijkstraAlgorithm(graph);
        disjkstra.execute(graph.firstVertex);
        LinkedList<Vertex> path = disjkstra.getPath(graph.lastVertex);
        for (Vertex vertex : path) {
            System.out.println(vertex.getNumber());
        }*/
    }
    
}
