package proyectoanalisisii.backtrackingAlgorithm;

import proyectoanalisisii.graph.Arc;
import proyectoanalisisii.graph.Graph;
import proyectoanalisisii.graph.Vertex;
import static proyectoanalisisii.graph.Graph.asignaciones;
import static proyectoanalisisii.graph.Graph.comparaciones;
import static proyectoanalisisii.graph.Graph.lineas;
import static proyectoanalisisii.graph.Graph.memory;
import static proyectoanalisisii.utils.Sizeof.OBJECTREF_SIZE;
import static proyectoanalisisii.utils.Sizeof.sizeof;

/**
 * Clase para el algoritmo de backtracking.
 */
public class Backtracking {

    private Graph graph;
    private int finalDistance;
    private String finalRoute;
    private long validRoutes;
    private String[] randomRoutes;
    private long[] ramdomRoutesNumbers = new long[5];

    public Backtracking(Graph graph) {
        this.graph = graph;
    }

    private void clearFinalRoute() {
        finalRoute = "";
        finalDistance = 0;
        validRoutes = 0;

        randomRoutes = new String[5];
        long maxRandom = calcValidRoutes();

        for (int i = 0; i < randomRoutes.length; i++) {
            ramdomRoutesNumbers[i] = (int) (Math.random() * maxRandom + 1);
        }
    }

    public long calcValidRoutes() {
        long graphTotal = graph.total - 2;
        long total = 1;

        for (long i = graphTotal; i >= 1; i--) {
            long mulResult = 1;
            for (long j = graphTotal; j >= i; j--) {
                mulResult *= j;
            }
            total += mulResult;
        }
        return total;
    }

    public void backtrackingHelper() {
        graph.clearMarks();
        graph.clearVars();
        clearFinalRoute();

        backtracking(graph.firstVertex, 0,
                String.valueOf(graph.firstVertex.getNumber()));
    }

    private void backtracking(Vertex origin, int totalDistance, String route) {
        // Nuevo String de ruta y nueva distancia, Vertex es una referencia
        // a un objeto, los String pot otro lado son inmutables así que hay 
        // que contarlos como uno nuevo cada vez que se cambian
        memory += Integer.SIZE + sizeof(route) + OBJECTREF_SIZE;

        comparaciones += 2;
        lineas++;
        if ((origin == null) || (origin.mark)) {
            lineas++;
            return;
        }

        comparaciones++;
        lineas++;
        if (origin == graph.lastVertex) {
            comparaciones += 2;
            lineas++;

            lineas++;
            asignaciones++;
            validRoutes++;

            lineas++;
            asignaciones++;
            comparaciones++;

            // Declaración de variable del loop
            memory += Integer.SIZE;
            for (int i = 0; i < ramdomRoutesNumbers.length; i++) {
                comparaciones++;
                lineas++;
                if (validRoutes == ramdomRoutesNumbers[i]) {
                    asignaciones++;
                    lineas++;
                    randomRoutes[i] = route;

                    lineas++;
                    break;
                }
                comparaciones++;
                asignaciones++;
                lineas++;
            }

            if ((finalRoute.isEmpty()) || (finalDistance > totalDistance)) {
                lineas++;
                asignaciones++;
                this.finalRoute = route;

                lineas++;
                asignaciones++;
                this.finalDistance = totalDistance;
            }
        }

        comparaciones++;
        lineas++;
        if (origin != graph.lastVertex) {
            lineas++;
            asignaciones++;
            origin.mark = true;
        }

        lineas++;
        asignaciones++;
        memory += OBJECTREF_SIZE;
        Arc tempArc = origin.firstArc;

        lineas++;
        comparaciones++;
        while (tempArc != null) {
            comparaciones++;
            lineas++;

            asignaciones++;
            backtracking(tempArc.getDestination(),
                    totalDistance + tempArc.getWeight(),
                    route + "-->" + tempArc.getDestination().getNumber());

            lineas++;
            asignaciones++;
            tempArc = tempArc.nextArc;
        }

        lineas++;
        asignaciones++;
        origin.mark = false;
    }

    public int getFinalDistance() {
        return finalDistance;
    }

    public void setFinalDistance(int finalDistance) {
        this.finalDistance = finalDistance;
    }

    public String getFinalRoute() {
        return finalRoute;
    }

    public void setFinalRoute(String finalRoute) {
        this.finalRoute = finalRoute;
    }

    public long getValidRoutesSize() {
        return validRoutes;
    }

    public String[] getRandomRoutes() {
        return randomRoutes;
    }
}
