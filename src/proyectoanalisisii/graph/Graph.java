package proyectoanalisisii.graph;

/**
 * Una clase que representa un grafo interconectado por varios {@link Arc}.
 */
public class Graph {

    // Algunas variables utilizadas
    public Vertex firstVertex;
    public Vertex lastVertex;

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
     */
    private void clearMarks() {
        Vertex aux = firstVertex;
        while (aux != null) {
            aux.mark = false;
            aux = aux.nextVertex;
        }
    }
    
    /**
     * 
     */
    public void printGraph(){
        for(Vertex aux = this.firstVertex;aux != null; aux = aux.nextVertex){
            for(Arc arcAux = aux.firstArc;arcAux != null; arcAux = arcAux.nextArc){
                System.out.println(aux.getNumber()+" -- "+arcAux.getWeight()+" --> "+arcAux.getDestination().getNumber());
            }
            System.out.println("");
        }
    }
    
     /**
     * Algoritmo voraz.
     */
    public void voracious(){
        Vertex aux = this.firstVertex;
        Arc auxArc;
        while (true) {  
            //busca el primer arco libre y de menor peso 
            Arc less = null;
            for(auxArc = aux.firstArc; auxArc != null;auxArc = auxArc.nextArc){
                if(!auxArc.getDestination().mark){
                    if(less == null){
                        less = auxArc;
                        continue;
                    }
                    
                    if(auxArc.getWeight() < less.getWeight())
                        less = auxArc;
                }
            }     
            
            if(less != null){
                aux.mark =true;
                System.out.print(aux.getNumber()+"->");
                aux = less.getDestination();
                if(aux == this.lastVertex){
                    System.out.print(aux.getNumber());
                    break;
                }
                continue;
            }
            
            break;
        }
    }
}
