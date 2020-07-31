package backtrackingAlgorithm;

import proyectoanalisisii.graph.Arc;
import proyectoanalisisii.graph.Graph;
import proyectoanalisisii.graph.Vertex;
import static proyectoanalisisii.graph.Graph.asignaciones;
import static proyectoanalisisii.graph.Graph.comparaciones;
import static proyectoanalisisii.graph.Graph.lineas;

/**
 * Clase para el algoritmo de backtracking.
 */
public class Backtracking {

    private Graph graph;
    private int finalDistance;
    private String finalRoute;
    private int validRoutes;
    private String[] randomRoutes;
    private int[] ramdomRoutesNumbers = new int[5];

    public Backtracking(Graph graph) {
        this.graph = graph;
    }

    private void clearFinalRoute() {
        finalRoute = "";
        finalDistance = 0;
        validRoutes = 0;

        randomRoutes = new String[5];
        int maxRandom = calcValidRoutes();

        for (int i = 0; i < randomRoutes.length; i++) {
            ramdomRoutesNumbers[i] = (int) (Math.random() * maxRandom + 1);
        }
    }

    private int calcValidRoutes() {
        int graphTotal = graph.total - 2;
        int total = 1;

        for (int i = graphTotal; i >= 1; i--) {
            int mulResult = 1;
            for (int j = graphTotal; j >= i; j--) {
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
            for (int j = 0; j < ramdomRoutesNumbers.length; j++) {
                comparaciones++;
                lineas++;
                if (validRoutes == ramdomRoutesNumbers[j]) {
                    asignaciones++;
                    lineas++;
                    randomRoutes[j] = route;

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

    public int getValidRoutesSize() {
        return validRoutes;
    }

    public String[] getRandomRoutes() {
        return randomRoutes;
    }
}
