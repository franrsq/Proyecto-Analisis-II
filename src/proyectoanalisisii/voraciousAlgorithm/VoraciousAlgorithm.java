package proyectoanalisisii.voraciousAlgorithm;

import proyectoanalisisii.graph.Arc;
import proyectoanalisisii.graph.Graph;
import static proyectoanalisisii.graph.Graph.asignaciones;
import static proyectoanalisisii.graph.Graph.comparaciones;
import static proyectoanalisisii.graph.Graph.lineas;
import static proyectoanalisisii.graph.Graph.memory;
import static proyectoanalisisii.utils.Sizeof.OBJECTREF_SIZE;
import static proyectoanalisisii.utils.Sizeof.sizeof;
import proyectoanalisisii.graph.Vertex;

/**
 *
 * @author Daniel
 */
public class VoraciousAlgorithm {

    private Graph graph;

    public VoraciousAlgorithm(Graph graph) {
        this.graph = graph;
    }

    /**
     * Algoritmo voraz.
     *
     * Formula: 9n[2]+13n+6. Memoria:
     */
    public void execute() {
        System.out.println("--Voraz--");
        lineas++;
        asignaciones++;

        memory += 2 * OBJECTREF_SIZE; // 2 referencias a memoria nuevas
        Vertex aux = this.graph.firstVertex; //1
        Arc auxArc;

        lineas++;
        asignaciones++;
        memory += Integer.SIZE;
        int weight = 0; //1

        lineas++;
        comparaciones++;
        while (true) {  //n
            lineas++;
            comparaciones++;

            //busca el primer arco libre y de menor peso 
            lineas++;
            asignaciones++;
            memory += OBJECTREF_SIZE; // referencia a memoria
            Arc less = null; // n

            lineas++;
            asignaciones++;
            comparaciones++;
            for (auxArc = aux.firstArc; auxArc != null; auxArc = auxArc.nextArc) { // n(2n+2)
                comparaciones++;
                lineas += 2;

                lineas += 2;
                comparaciones++;
                if (!auxArc.getDestination().mark) {// n(n)
                    lineas++;
                    comparaciones++;
                    if (less == null) { // n(n)
                        lineas++;
                        asignaciones++;
                        less = auxArc; // 1
                        lineas++;
                        continue; // 1
                    }
                    lineas++;
                    comparaciones++;
                    if (auxArc.getWeight() < less.getWeight()) { // n(n)
                        lineas++;
                        asignaciones++;
                        less = auxArc; // n(n)
                    }
                }
            }

            lineas++;
            comparaciones++;
            if (less != null) { // n
                lineas++;
                asignaciones++;
                aux.mark = true; // n

                System.out.print(aux.getNumber() + "->");

                lineas++;
                asignaciones++;
                weight += less.getWeight(); // n

                lineas++;
                asignaciones++;
                aux = less.getDestination(); // n

                comparaciones++;
                lineas++;
                if (aux == this.graph.lastVertex) { // n
                    System.out.println(aux.getNumber() + " Peso:" + weight);
                    this.graph.clearMarks(); // 3n+2
                    lineas++;
                    break; // 1
                }
                lineas++;
                continue; // n
            }
            lineas++;
            break; // 1
        }
    }
}
