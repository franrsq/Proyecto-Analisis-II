package dinamicAlgorithm;

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

    public void execute(Vertex source) {
        Graph.lineas++;
        Graph.asignaciones++;
        settledNodes = new HashSet<Vertex>();
        Graph.asignaciones++;
        Graph.lineas++;
        unSettledNodes = new HashSet<Vertex>();
        Graph.asignaciones++;
        Graph.lineas++;
        distance = new HashMap<Vertex, Integer>();
        Graph.asignaciones++;
        Graph.lineas++;
        predecessors = new HashMap<Vertex, Vertex>();
        Graph.asignaciones++;
        Graph.lineas++;
        distance.put(source, 0);
        Graph.asignaciones++;
        Graph.lineas++;
        unSettledNodes.add(source);
        Graph.lineas++;
        Graph.comparaciones++;
        while (unSettledNodes.size() > 0) {
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.asignaciones++;
            Vertex node = getMinimum(unSettledNodes);
            Graph.lineas++;
            Graph.asignaciones++;
            settledNodes.add(node);
            Graph.lineas++;
            Graph.asignaciones++;
            unSettledNodes.remove(node);
            Graph.lineas++;
            Graph.asignaciones++;
            findMinimalDistances(node);
        }
        System.out.println("Fase 2 se encuentran las distancias");
       
        Iterator<Map.Entry<Vertex, Integer>> i = distance.entrySet().iterator(); 
        while(i.hasNext()){
            Vertex key = i.next().getKey();
            System.out.println(key.getNumber()+", "+distance.get(key));
        }
    
        System.out.println("Fase 3 se encuentran los predecesores");
        Iterator<Map.Entry<Vertex, Vertex>> p = predecessors.entrySet().iterator(); 
        while(p.hasNext()){
            Vertex key = p.next().getKey();
            System.out.println(key.getNumber()+", "+predecessors.get(key).getNumber());
        }
    }

    private void findMinimalDistances(Vertex node) {
        Graph.lineas++;
        Graph.asignaciones++;
        List<Vertex> adjacentNodes = getNeighbors(node);
        Graph.lineas++;
        Graph.comparaciones++;
        for (Vertex target : adjacentNodes) {
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.comparaciones++;
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                Graph.lineas++;
                Graph.asignaciones++;
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                Graph.lineas++;
                Graph.asignaciones++;
                predecessors.put(target, node);
                Graph.lineas++;
                Graph.asignaciones++;
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Vertex node, Vertex target) {
        Graph.lineas++;
        Graph.comparaciones++;
        for (Arc edge : edges) {
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.comparaciones++;
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                Graph.lineas++;
                Graph.asignaciones++;
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        Graph.lineas++;
        Graph.asignaciones++;
        List<Vertex> neighbors = new ArrayList<Vertex>();
        Graph.lineas++;
        Graph.comparaciones++;
        for (Arc edge : edges) {
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.comparaciones++;
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                Graph.lineas++;
                Graph.asignaciones++;
                neighbors.add(edge.getDestination());
            }
        }
        Graph.asignaciones++;
        Graph.lineas++;
        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Graph.lineas++;
        Graph.asignaciones++;
        Vertex minimum = null;
        Graph.lineas++;
        Graph.comparaciones++;
        for (Vertex vertex : vertexes) {
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.comparaciones++;
            if (minimum == null) {
                Graph.lineas++;
                Graph.asignaciones++;
                minimum = vertex;
            } else {
                Graph.lineas++;
                Graph.comparaciones++;
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    Graph.lineas++;
                    Graph.asignaciones++;
                    minimum = vertex;
                }
            }
        }
        Graph.asignaciones++;
        Graph.lineas++;
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        Graph.lineas++;
        Graph.asignaciones++;
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Vertex destination) {
        Graph.lineas++;
        Graph.asignaciones++;
        Integer d = distance.get(destination);
        Graph.lineas++;
        Graph.comparaciones++;
        if (d == null) {
            Graph.lineas++;
            Graph.asignaciones++;
            return Integer.MAX_VALUE;
        } else {
            Graph.asignaciones++;
            Graph.lineas++;
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        System.out.println("Fase 4 se encuentra la mejor ruta");
        Graph.lineas++;
        Graph.asignaciones++;
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Graph.lineas++;
        Graph.asignaciones++;
        Vertex step = target;
        // check if a path exists
        Graph.lineas++;
        Graph.comparaciones++;
        if (predecessors.get(step) == null) {
            Graph.lineas++;
            return null;
        }
        Graph.lineas++;
        Graph.asignaciones++;
        path.add(step);
        Graph.lineas++;
        Graph.comparaciones++;
        while (predecessors.get(step) != null) {
            Graph.lineas++;
            Graph.asignaciones++;
            step = predecessors.get(step);
            Graph.lineas++;
            Graph.asignaciones++;
            path.add(step);
        }
        // Put it into the correct order
        Graph.lineas++;
        Graph.asignaciones++;
        Collections.reverse(path);
        Graph.asignaciones++;
        Graph.lineas++;
        return path;
    }

}
