package proyectoanalisisii;

import backtrackingAlgorithm.Backtracking;
import dinamicAlgorithm.DijkstraAlgorithm;
import geneticAlgorithm.GeneticAlgorithm;
import java.util.LinkedList;
import proyectoanalisisii.graph.Graph;
import proyectoanalisisii.graph.Vertex;
import voraciousAlgorithm.VoraciousAlgorithm;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Graph graph = new Graph();
        graph.createGraph(10);

        //Voraz
        VoraciousAlgorithm voracius = new VoraciousAlgorithm(graph);
        long start = System.currentTimeMillis();
        voracius.execute();
        long finish = System.currentTimeMillis();
        graph.printVars(finish - start);

        // Se limpian los contadores
        graph.clearVars();

        //Genetico
        GeneticAlgorithm genetic = new GeneticAlgorithm(graph);
        start = System.currentTimeMillis();
        genetic.execute();
        genetic.printRoute();
        finish = System.currentTimeMillis();
        graph.printVars(finish - start);

        // Se limpian los contadores
        graph.clearVars();

        //Dinamico
        DijkstraAlgorithm disjkstra = new DijkstraAlgorithm(graph);
        start = System.currentTimeMillis();
        disjkstra.execute(graph.firstVertex);
        LinkedList<Vertex> path = disjkstra.getPath(graph.lastVertex);
        finish = System.currentTimeMillis();
        System.out.println("--Dinamico--");
        graph.printVars(finish - start);
        System.out.print("Mejor ruta:");
        int weight = 0;
        for (int i = 0; i < path.size(); i++) {
            System.out.print("-->" + path.get(i).getNumber());
            if (i < path.size() - 1) {
                weight += path.get(i).getArcToVertex(path.get(i + 1)).getWeight();
            }
        }
        System.out.print(" Peso: " + weight);
        System.out.println("\n");

        // Se limpian los contadores
        graph.clearVars();

        //Backtracking
        Backtracking backtracking = new Backtracking(graph);
        System.out.println("--Backtracking--");
        start = System.currentTimeMillis();
        backtracking.backtrackingHelper();
        finish = System.currentTimeMillis();
        System.out.println(backtracking.getFinalRoute() + " Peso: "
                + backtracking.getFinalDistance());
        System.out.println("Rutas válidas: " + backtracking.getValidRoutesSize());
        System.out.println("Rutas al azar:");
        for (String ruta : backtracking.getRandomRoutes()) {
            System.out.println("    " + ruta);
        }
        graph.printVars(finish - start);
    }

}
