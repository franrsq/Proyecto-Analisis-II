package proyectoanalisisii.geneticAlgorithm;

import proyectoanalisisii.graph.Arc;
import proyectoanalisisii.graph.Graph;
import proyectoanalisisii.graph.Vertex;
import proyectoanalisisii.utils.Sizeof;
import static proyectoanalisisii.utils.Sizeof.sizeof;

/**
 *
 * @author Daniel
 */
public class GeneticAlgorithm {

    private Graph graph;
    public Vertex[] shortRoute;
    public int weight;

    public GeneticAlgorithm(Graph graph) {
        this.graph = graph;
        this.graph.clearMarks();
        this.graph.clearVars();
    }

    /**
     * Genera rutas aletorias y ejecuta el algoritmo genetico Formula:
     * (17x[3]+33x[2]+126x+256)/8
     */
    public void execute() {
        // ((7n[3]-5n[2]+14n)/4)+4*
        Graph.asignaciones++;
        Graph.lineas++;
        Graph.memory += Integer.SIZE;
        int mid = this.graph.total >>> 1; // 1

        Graph.lineas++;
        Graph.asignaciones++;
        Vertex[][] randomRoutes = new Vertex[mid >>> 1][mid]; //1
        Graph.memory += sizeof(randomRoutes);

        Graph.lineas++;
        Graph.asignaciones++;
        Graph.comparaciones++;
        Graph.memory += Integer.SIZE;
        for (int i = 0; i < mid >>> 1; i++) { // n+2 
            Graph.lineas++;
            Graph.comparaciones++;

            Graph.lineas++;
            Graph.asignaciones++;
            Vertex[] randomRoute = this.graph.generateRandomRoute(mid); // (7n[3]-8n[2]+6n)/4
            Graph.memory += sizeof(randomRoute);

            Graph.lineas++;
            Graph.asignaciones++;
            randomRoutes[i] = randomRoute; // n/4
            Graph.lineas++;
            this.graph.clearMarks(); // (3n[2]+2n)/4
        }

        System.out.println("Generacion: 0");
        // (3x[3]+43x[2]+98x+224)/8*
        Graph.lineas++;
        this.printRoutes(randomRoutes); // (2n[2]+7x+2)/2 

        Graph.lineas++;
        Graph.asignaciones++;
        Graph.comparaciones++;
        Graph.memory += Integer.SIZE;
        for (int i = 0; i < 4; i++) { // 9
            Graph.lineas++;
            Graph.comparaciones++;

            System.out.println("Generacion: " + (i + 1));

            Graph.lineas++;
            Graph.comparaciones++;
            Graph.asignaciones++;
            Graph.memory += Integer.SIZE;
            for (int index = 0; index < randomRoutes.length; index++) { // (9x+36)/2
                Graph.lineas++;
                Graph.comparaciones++;

                Graph.lineas++;
                Graph.asignaciones++;
                Vertex[] route = randomRoutes[index]; //n/4
                Graph.memory += sizeof(route);

                Graph.lineas++;
                Graph.asignaciones++;
                route = partiallyMatchedCrossover(route); // (n/4) ((3x[2]+35x+28)/2)

                Graph.lineas++;
                Graph.asignaciones++;
                randomRoutes[index] = route; // n/4
            }
            System.out.println("");
        }
    }

    /**
     * Imprime la ruta más corta encontrada
     */
    public void printRoute() {
        System.out.print("Mejor Ruta:");
        for (int i = 0; i < this.shortRoute.length; i++) {
            if (i == this.shortRoute.length - 1) {
                System.out.print(shortRoute[i].getNumber());
            } else {
                System.out.print(shortRoute[i].getNumber() + "-->");
            }
        }
        System.out.println(" Peso: " + this.weight);
    }

    /**
     * Imprime todas las rutas generadas
     *
     * @param routes Formula: (2n[2]+7x+2)/2
     */
    private void printRoutes(Vertex[][] routes) {
        Graph.lineas++;
        Graph.comparaciones++;
        Graph.memory += Integer.SIZE;
        for (int i = 0; i < routes.length; i++) { // 2n+2
            Graph.lineas++;
            Graph.comparaciones++;

            Graph.lineas++;
            Graph.comparaciones++;
            Graph.memory += Integer.SIZE;
            for (int a = 0; a < routes[i].length - 1; a++) { // n(1+ (n/2)+ (n-2)/2 ) == n[2]
                Graph.lineas++;
                Graph.comparaciones++;

                Graph.lineas++;
                System.out.print(routes[i][a].getNumber() + "-->"); //(n-2)/2
            }
            Graph.lineas++;
            System.out.println(routes[i][routes[i].length - 1].getNumber() + ""); //n
        }
    }

    /**
     * Algoritmo genetico, cambia parcialmente una zona de la ruta
     *
     * @param route Ruta para mutar
     * @return Ruta mutada. Formula: (3x[2]+35x+28)/2
     */
    private Vertex[] partiallyMatchedCrossover(Vertex[] route) {
        //4*
        Graph.lineas += 4;
        Graph.asignaciones += 4;
        Graph.memory += 4 * Integer.SIZE;
        int min = (int) (Math.random() * (route.length >>> 1)) + 1;   //1
        int max = (int) (Math.random() * (route.length - min - 1)) + min; //1
        int y = max; //1
        int weight = 0; //1
        // (3n[2]+35x+10)/2 *
        Graph.lineas++;
        Graph.comparaciones++;
        Graph.memory += Integer.SIZE;
        for (int i = 0; i < route.length; i++) { //n+2
            Graph.lineas++;
            Graph.comparaciones++;
            //3n+5*
            Graph.lineas++;
            Graph.comparaciones++;
            if (i >= min && i < max) { // n/2
                Graph.lineas += 5;
                Graph.asignaciones += 5;
                Graph.memory += 2 * Sizeof.OBJECTREF_SIZE;
                Vertex vertex1 = route[i]; // (n+2)/2
                Vertex vertex2 = route[y]; // (n+2)/2
                route[i] = vertex2; // (n+2)/2
                route[y] = vertex1; // (n+2)/2
                y--; // -(n+2)/2
            }
            //(3n-14+3n[2])/2*
            Graph.lineas++;
            Graph.comparaciones++;
            if (i < route.length - 1) { // n/2
                Graph.lineas += 5;
                Graph.asignaciones += 4;
                Graph.memory += 3 * Sizeof.OBJECTREF_SIZE;
                Vertex aux = route[i]; // (n-2)/2
                Vertex aux2 = route[i + 1];  // (n-2)/2
                Arc arc = aux.getArcToVertex(aux2);  // ((n-2)/2)(3n + 4)
                System.out.print(aux.getNumber() + " -[" + arc.getWeight() + "]-> "); //  (n-2)/2
                weight += arc.getWeight(); // (n-2)/2
            } else {
                Graph.lineas++;
                System.out.print(route[i].getNumber()); //1
            }
        }
        //5
        Graph.lineas++;
        Graph.comparaciones++;
        if (this.shortRoute == null || weight < this.weight) { //1
            Graph.lineas += 2;
            Graph.asignaciones += 2;
            this.shortRoute = route; //1
            this.weight = weight; //1
        }
        Graph.lineas += 2;
        Graph.asignaciones++;
        System.out.println(" weight: " + weight); //1
        return route; //1 
    }
}
