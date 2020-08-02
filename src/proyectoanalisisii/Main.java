package proyectoanalisisii;

import backtrackingAlgorithm.Backtracking;
import dinamicAlgorithm.DijkstraAlgorithm;
import geneticAlgorithm.GeneticAlgorithm;
import java.util.LinkedList;
import proyectoanalisisii.graph.Graph;
import proyectoanalisisii.graph.Vertex;
import prunningAlgorithm.PrunningAlgorithm;
import voraciousAlgorithm.VoraciousAlgorithm;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Graph graph = new Graph();
        graph.createGraph(10);

        //Voraz
        long before=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(); 
        VoraciousAlgorithm voracius = new VoraciousAlgorithm(graph);
        long start = System.currentTimeMillis();
        voracius.execute();
        long finish = System.currentTimeMillis();
        long after=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(); 
        graph.printVars(finish - start,after - before);

        // Se limpian los contadores
        graph.clearVars();

        //Genetico
        before=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        GeneticAlgorithm genetic = new GeneticAlgorithm(graph);
        start = System.currentTimeMillis();
        genetic.execute();
        genetic.printRoute();
        finish = System.currentTimeMillis();
        after=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(); 
        graph.printVars(finish - start,after - before);

        // Se limpian los contadores
        graph.clearVars();

        //Dinamico
        before=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        DijkstraAlgorithm disjkstra = new DijkstraAlgorithm(graph);
        start = System.currentTimeMillis();
        disjkstra.execute(graph.firstVertex);
        LinkedList<Vertex> path = disjkstra.getPath(graph.lastVertex);
        finish = System.currentTimeMillis();
        System.out.println("--Dinamico--");
        after=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(); 
        graph.printVars(finish - start,after - before);
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
        before=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
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
        after=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(); 
        graph.printVars(finish - start,after - before);

        // Se limpian los contadores
        graph.clearVars();

        //Poda
        before=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        PrunningAlgorithm prunning = new PrunningAlgorithm(graph);
        System.out.println("--Poda--");
        start = System.currentTimeMillis();
        prunning.prunningHelper();
        finish = System.currentTimeMillis();
        System.out.println(prunning.getFinalRoute() + " Peso: "
                + prunning.getFinalDistance());
        System.out.println("Cantidad rutas podas: "
                + prunning.getPrunnedRoutesQuantity());
        System.out.println("Rutas podas (5):");
        for (String ruta : prunning.getPrunnedRoutes()) {
            System.out.println("    " + ruta);
        }
        after=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(); 
        graph.printVars(finish - start,after - before);

        long last=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(); 
        System.out.println(last);
        
    }

}
