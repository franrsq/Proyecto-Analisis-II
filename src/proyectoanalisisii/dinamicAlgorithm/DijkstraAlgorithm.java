package proyectoanalisisii.dinamicAlgorithm;

/**
 *
 * @author pache
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import proyectoanalisisii.graph.Arc;
import proyectoanalisisii.graph.Graph;
import proyectoanalisisii.graph.Vertex;
import proyectoanalisisii.utils.Sizeof;
import static proyectoanalisisii.utils.Sizeof.sizeof;

public class DijkstraAlgorithm {

    private final List<Vertex> nodes;
    private final List<Arc> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;

    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        System.out.println("Fase 1 se llenan los las listas con la informacion de los grafos");
        Vertex aux = graph.firstVertex;
        this.nodes = new ArrayList<Vertex>();
        while (aux != graph.lastVertex) {
            this.nodes.add(aux);
            aux = aux.nextVertex;
        }
        this.nodes.add(aux);

        aux = graph.firstVertex;
        Arc auxArco;
        this.edges = new ArrayList<Arc>();
        while (aux != graph.lastVertex) {
            auxArco = aux.firstArc;
            while (auxArco.nextArc != null) {
                edges.add(auxArco);
                auxArco = auxArco.nextArc;
            }
            edges.add(auxArco);
            aux = aux.nextVertex;
        }
    }

    public void execute(Vertex source) {  // 6n^3+28n^2+10n+7
        Graph.lineas++;
        Graph.asignaciones++;
        settledNodes = new HashSet<Vertex>();  //1
        Graph.asignaciones++;
        Graph.lineas++;
        unSettledNodes = new HashSet<Vertex>();  //1
        Graph.asignaciones++;
        Graph.lineas++;
        distance = new HashMap<Vertex, Integer>();  //1
        Graph.asignaciones++;
        Graph.lineas++;
        predecessors = new HashMap<Vertex, Vertex>();  //1
        Graph.asignaciones++;
        Graph.lineas++;
        distance.put(source, 0);  //1
        Graph.asignaciones++;
        Graph.lineas++;
        unSettledNodes.add(source); //1
        Graph.lineas++;
        Graph.comparaciones++;
        while (unSettledNodes.size() > 0) { //n+1
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.asignaciones++;
            Vertex node = getMinimum(unSettledNodes);  //(9n+4) = 9n^2+4n
            Graph.lineas++;
            Graph.asignaciones++;
            settledNodes.add(node); //n
            Graph.lineas++;
            Graph.asignaciones++;
            unSettledNodes.remove(node);  //n
            Graph.lineas++;
            Graph.asignaciones++;
            findMinimalDistances(node);  //(6n^2+19n+3)n = 6n^3+19n^2+3n
        }
        Graph.memory += sizeof(distance);
        Graph.memory += sizeof(predecessors);
        Graph.memory += sizeof(settledNodes);
        System.out.println("Fase 2 se encuentran las distancias");

        Graph.memory += Sizeof.OBJECTREF_SIZE;
        Iterator<Map.Entry<Vertex, Integer>> i = distance.entrySet().iterator();
        while (i.hasNext()) {
            Graph.memory += Sizeof.OBJECTREF_SIZE;
            Vertex key = i.next().getKey();
            System.out.println(key.getNumber() + ", " + distance.get(key));
        }

        System.out.println("Fase 3 se encuentran los predecesores");
        Graph.memory += Sizeof.OBJECTREF_SIZE;
        Iterator<Map.Entry<Vertex, Vertex>> p = predecessors.entrySet().iterator();
        while (p.hasNext()) {
            Graph.memory += Sizeof.OBJECTREF_SIZE;
            Vertex key = p.next().getKey();
            System.out.println(key.getNumber() + ", " + predecessors.get(key).getNumber());
        }
    }

    private void findMinimalDistances(Vertex node) {  //6n^2+19n+3
        Graph.lineas++;
        Graph.asignaciones++;
        List<Vertex> adjacentNodes = getNeighbors(node);  //3n+2
        Graph.lineas++;
        Graph.comparaciones++;
        for (Vertex target : adjacentNodes) {  //n+1
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.comparaciones++;
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {  //n(3n+8) = 3n^2+8n
                Graph.lineas++;
                Graph.asignaciones++;
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target)); //n(3n+5) = 3n^2+5n
                Graph.lineas++;
                Graph.asignaciones++;
                predecessors.put(target, node);  //n
                Graph.lineas++;
                Graph.asignaciones++;
                unSettledNodes.add(target);  //n
            }
        }

    }

    private int getDistance(Vertex node, Vertex target) { //3n+2
        Graph.lineas++;
        Graph.comparaciones++;
        for (Arc edge : edges) {  //n+1
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.comparaciones++;
            Graph.lineas++;
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {  //2n
                Graph.lineas++;
                Graph.asignaciones++;
                return edge.getWeight(); //1
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) { //4n+3
        Graph.lineas++;
        Graph.asignaciones++;
        List<Vertex> neighbors = new ArrayList<Vertex>();  //1
        Graph.lineas++;
        Graph.comparaciones++;
        for (Arc edge : edges) {  //n+1
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.lineas++;
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {  //2n
                Graph.lineas++;
                Graph.asignaciones++;
                neighbors.add(edge.getDestination()); //n
            }
        }
        Graph.asignaciones++;
        Graph.lineas++;
        return neighbors;  //1
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {  //9n+4
        Graph.lineas++;
        Graph.asignaciones++;
        Vertex minimum = null;  //1
        Graph.lineas++;
        Graph.comparaciones++;
        for (Vertex vertex : vertexes) {  //n+1
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.comparaciones++;
            Graph.lineas++;
            if (minimum == null) {  //n
                Graph.lineas++;
                Graph.asignaciones++;
                minimum = vertex; //1
            } else {
                Graph.lineas++;
                Graph.comparaciones++;
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {  //6n
                    Graph.lineas++;
                    Graph.asignaciones++;
                    minimum = vertex;  //n
                }
            }
        }
        Graph.asignaciones++;
        Graph.lineas++;
        return minimum;  //1
    }

    private boolean isSettled(Vertex vertex) { //1
        Graph.lineas++;
        Graph.asignaciones++;
        return settledNodes.contains(vertex); //1
    }

    private int getShortestDistance(Vertex destination) { //3
        Graph.lineas++;
        Graph.asignaciones++;
        Integer d = distance.get(destination);  //1
        Graph.lineas++;
        Graph.comparaciones++;
        if (d == null) {  //1
            Graph.lineas++;
            Graph.asignaciones++;
            return Integer.MAX_VALUE;  //1
        } else {
            Graph.asignaciones++;
            Graph.lineas++;
            return d; //1
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertex> getPath(Vertex target) {  //4n+8
        System.out.println("Fase 4 se encuentra la mejor ruta");
        Graph.lineas++;
        Graph.asignaciones++;
        LinkedList<Vertex> path = new LinkedList<Vertex>(); //1
        Graph.lineas++;
        Graph.asignaciones++;
        Vertex step = target;  //1
        // check if a path exists
        Graph.lineas++;
        Graph.comparaciones++;
        if (predecessors.get(step) == null) {  //1
            Graph.lineas++;
            return null;  //1
        }
        Graph.lineas++;
        Graph.asignaciones++;
        path.add(step);  //1
        Graph.lineas++;
        Graph.comparaciones++;
        while (predecessors.get(step) != null) {  //n+1
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.asignaciones++;
            step = predecessors.get(step);  //n
            Graph.lineas++;
            Graph.asignaciones++;
            path.add(step);  //n
        }
        // Put it into the correct order
        Graph.lineas++;
        Graph.asignaciones++;
        Collections.reverse(path);  //1
        Graph.asignaciones++;
        Graph.lineas++;
        return path;  //1
    }

}
