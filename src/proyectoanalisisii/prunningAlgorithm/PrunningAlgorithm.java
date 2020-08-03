package proyectoanalisisii.prunningAlgorithm;

import proyectoanalisisii.graph.Arc;
import proyectoanalisisii.graph.Graph;
import static proyectoanalisisii.graph.Graph.asignaciones;
import static proyectoanalisisii.graph.Graph.comparaciones;
import static proyectoanalisisii.graph.Graph.lineas;
import static proyectoanalisisii.graph.Graph.memory;
import proyectoanalisisii.graph.Vertex;
import static proyectoanalisisii.utils.Sizeof.OBJECTREF_SIZE;
import static proyectoanalisisii.utils.Sizeof.sizeof;

public class PrunningAlgorithm {

    private Graph graph;
    private int finalDistance; // Distancia de la ruta final
    private String finalRoute; // Ruta final
    private int prunnedRoutesQuantity; // Cantidad de rutas podadas
    private String[] prunnedRoutes = new String[5];

    public PrunningAlgorithm(Graph graph) {
        this.graph = graph;
    }

    private void clearFinalRoute() {
        finalRoute = "";
        finalDistance = 0;
        prunnedRoutesQuantity = 0;
    }

    public void prunningHelper() {
        graph.clearMarks();
        graph.clearVars();
        clearFinalRoute();
        prunning(graph.firstVertex, 0,
                String.valueOf(graph.firstVertex.getNumber()));
    }

    private void prunning(Vertex origin, int totalDistance, String route) {
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

        comparaciones += 2;
        lineas++;
        if (finalDistance != 0 && totalDistance > finalDistance) {
            comparaciones++;
            lineas++;
            if (prunnedRoutesQuantity < prunnedRoutes.length) {
                asignaciones++;
                lineas++;
                prunnedRoutes[prunnedRoutesQuantity] = route + " Peso: "
                        + totalDistance + " > " + finalDistance;
                memory += sizeof(prunnedRoutes[prunnedRoutesQuantity]);
            }

            asignaciones++;
            lineas++;
            prunnedRoutesQuantity++;

            lineas++;
            return;
        }

        comparaciones++;
        lineas++;
        if (origin == graph.lastVertex) {
            comparaciones += 2;
            lineas++;

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
            prunning(tempArc.getDestination(),
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

    public int getPrunnedRoutesQuantity() {
        return prunnedRoutesQuantity;
    }

    public String[] getPrunnedRoutes() {
        return prunnedRoutes;
    }
}
