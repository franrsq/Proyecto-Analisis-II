package proyectoanalisisii.graph;

/**
 * Una clase que representa un grafo interconectado por varios {@link Arc}.
 */
public class Graph {

    // Algunas variables utilizadas
    public Vertex firstVertex;
    public Vertex lastVertex;

    public int total;

    public static long asignaciones, comparaciones, lineas;

    /**
     * Inserta un {@link Vertex} al grafo.
     *
     * @param name el nombre es el numero que se le asigna a cada {@link Vertex}
     */
    public void addVertex(int name) {
        Vertex newVertex = new Vertex(name);

        if (firstVertex == null) {
            firstVertex = newVertex;
            lastVertex = newVertex;
            return;
        }
        lastVertex.nextVertex = newVertex;
        lastVertex = newVertex;
    }

    /**
     * Inserta un {@link Arc} de un {@link Vertex} a otro.
     *
     * @param source {@link Vertex} de origen
     * @param destination {@link Vertex} de destino
     */
    public void addArc(Vertex source, Vertex destination) {
        Arc newArc = new Arc(destination);
        newArc.source = source;
        if (source.firstArc == null) {
            source.firstArc = newArc;
        } else {
            newArc.nextArc = source.firstArc;
            source.firstArc = newArc;
        }
    }

    /**
     * Método que crea los {@link Vertex} y hace las conexiones entre ellos para
     * hacer un grafo conexo.
     *
     * @param amount cantidad de vertices a insertar
     * @return true si todo salió bien
     */
    public boolean createGraph(int amount) {
        this.total = amount;
        for (int i = 1; i <= amount; i++) {
            addVertex(i);
        }
        Vertex aux = firstVertex;
        Vertex aux2 = firstVertex;

        // Se omite el ultimo vertice para que solo tenga arcos de llegada
        while (aux.nextVertex != null) {
            while (aux2 != null) {
                // Si no son el mismo vertice y el destino no es el primero
                if ((aux != aux2) && (aux2 != firstVertex)) {
                    addArc(aux, aux2);
                }
                aux2 = aux2.nextVertex;
            }
            aux2 = firstVertex;
            aux = aux.nextVertex;
        }
        return true;
    }

    /**
     * Limpia las marcas de todos los vertices.
     *
     * Es linea de formula 3n+2
     */
    public void clearMarks() {
        lineas++;
        asignaciones++;
        Vertex aux = firstVertex;   //1

        lineas++;
        comparaciones++;
        while (aux != null) {       // n+1
            lineas++;
            comparaciones++;
            aux.mark = false;       // n

            lineas++;
            comparaciones++;
            aux = aux.nextVertex;   // n
        }
    }

    /**
     * Limpia las variables de los contadores.
     */
    public void clearVars() {
        this.asignaciones = 0;
        this.comparaciones = 0;
        this.lineas = 0;
    }

    /**
     *
     */
    public void printGraph() {
        for (Vertex aux = this.firstVertex; aux != null; aux = aux.nextVertex) {
            for (Arc arcAux = aux.firstArc; arcAux != null; arcAux = arcAux.nextArc) {
                System.out.println(aux.getNumber() + " -- " + arcAux.getWeight() + " --> " + arcAux.getDestination().getNumber());
            }
            System.out.println("");
        }
    }

    public void printVars(float time,long memory) {
        System.out.print("Timepo transcurrido: ");
        System.out.printf("Tiempo = %.3f S\n", time / 1000);
        System.out.print("Comparaciones: ");
        System.out.println(comparaciones);
        System.out.print("Asignaciones: ");
        System.out.println(asignaciones);
        System.out.print("Lineas de codigo: ");
        System.out.println(lineas);
        System.out.print("Memoria: ");
        System.out.println(memory/1024+" KB");
        System.out.println("\n");
        comparaciones = 0;
        asignaciones = 0;
        lineas = 0;
    }

    /**
     * Recuper el vertice en la posicion indicad
     *
     * @param index posicion del vertice
     * @return vetice en la posicion o NULL si no existe. Formula: 3n + 6
     */
    public Vertex getVertexInPosition(int index) {
        lineas++;
        asignaciones++;
        Vertex aux = this.firstVertex; //

        lineas++;
        asignaciones++;
        comparaciones++;
        for (int i = 1; i <= index; i++) { // 1 + n+1 +n
            lineas++;
            comparaciones++;

            lineas++;
            asignaciones++;
            aux = aux.nextVertex; // n
        }
        lineas++;
        asignaciones++;
        return aux; //1
    }

    /**
     * Genera una Ruta aleatoria.
     *
     * @param length Longitud de la ruta. Formula: 7n[2]-8n+6 (Cuadratica).
     */
    public Vertex[] generateRandomRoute(int length) {
        lineas++;
        asignaciones++;
        Vertex randomRoute[] = new Vertex[length]; //1

        lineas++;
        asignaciones++;
        randomRoute[0] = this.firstVertex; //1

        lineas++;
        asignaciones++;
        comparaciones++;
        for (int i = 1; i < length - 1; i++) { //1 +n-1 + n-2
            lineas++;
            comparaciones++;

            lineas++;
            asignaciones++;
            Vertex randomVertex = this.getRandomVertex(); // (n-2)(7n+2)

            lineas++;
            asignaciones++;
            randomVertex.mark = true; //n-2

            lineas++;
            asignaciones++;
            randomRoute[i] = randomVertex; //n-2
        }
        lineas++;
        asignaciones++;
        randomRoute[length - 1] = this.lastVertex; //1

        lineas++;
        asignaciones++;
        return randomRoute; //1
    }

    /**
     * Recupera de manera aleatoria cualquier vertice sin marca. nota:Ignora el
     * vertice de inicio y fin.
     *
     * @return Vertice Aleatorio. Forula: 7n+2
     */
    public Vertex getRandomVertex() {
        lineas++;
        asignaciones++;
        int randomPosition = (int) (Math.random() * (this.total - 2)) + 1; //1

        lineas++;
        asignaciones++;
        Vertex randomVertex = this.getVertexInPosition(randomPosition); // 3n + 6

        lineas++;
        comparaciones++;
        if (randomVertex.mark) { // 1
            lineas++;
            asignaciones++;
            randomVertex = this.getFirstVertexAvailable(); // 4n-7
        }
        lineas++;
        asignaciones++;
        return randomVertex; //1
    }

    /**
     * Consigue el primer vertice disponible sin marca nota: Ignora el vertice
     * de inicio y fin.
     *
     * @return Formula: 4n-7
     */
    public Vertex getFirstVertexAvailable() {
        lineas++;
        asignaciones++;
        Vertex aux = this.firstVertex.nextVertex; //1
        lineas++;
        asignaciones++;
        comparaciones++;
        for (int i = 1; i < this.total - 2; i++) { // 1+ n-2 +n-3
            lineas++;
            comparaciones++;

            lineas++;
            comparaciones++;
            if (!aux.mark) { // n-3
                lineas++;
                asignaciones++;
                return aux;  //1
            }
            lineas++;
            asignaciones++;
            aux = aux.nextVertex; //n-3
        }
        lineas++;
        asignaciones++;
        return null; // 1
    }

}
