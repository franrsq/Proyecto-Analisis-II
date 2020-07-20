package proyectoanalisisii;

import proyectoanalisisii.graph.Graph;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Graph graph = new Graph();
        graph.createGraph(10);
        graph.printGraph();
        graph.voracious();
        
    }

}
