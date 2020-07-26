package proyectoanalisisii.graph;

/**
 * Representa los vertices de un {@link Graph}.
 */
public class Vertex {

    public Vertex nextVertex;
    public Arc firstArc;
    private int totalArcs;
    public boolean mark;

    private int number;

    /**
     * Crea un nuevo a vertice.
     *
     * @param number numero del vertice
     */
    public Vertex(int number) {
        this.number = number;
        this.mark = false;
    }

    // Getters and setters
    public int getNumber() {
        return number;
    }

    public void sumArc(){
        this.totalArcs++;
    }
    
    public int getArcCount(){
        return this.totalArcs;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    /**
     * Retorna un arco en la posicion especificada.
     * @param index posicion del arco
     * @return Arc si esta en el rango o Null si esta afuera del rango.
     */
    public Arc getArcInPosition(int index){
        Graph.lineas++;
        Graph.comparaciones+=2;
        if(this.totalArcs-1 < 0 || index > this.totalArcs){
            Graph.lineas++;
            return null;
        }
        Graph.lineas++;
        Graph.asignaciones++;
        Arc aux = this.firstArc;
        
        Graph.lineas++;
        Graph.asignaciones++;
        for( int i = 0; i<= index; i++ ){
            Graph.lineas++;
            Graph.comparaciones++;
            Graph.lineas++;
            Graph.asignaciones++;
            aux = aux.nextArc;
        } 
        Graph.lineas++;
        return aux;
    }
    
    /**
     * Busca el arco que apunta al vertices espcificado
     * @param vertex vertice que apunta el arco 
     * @return Arc si se encunetra el arco o Null si no se encuntra .
     * Formula: 3n + 4.
     */
    public Arc getArcToVertex(Vertex vertex){
        Graph.asignaciones++;
        Graph.lineas++;
        for( Arc aux = this.firstArc; aux != null; aux = aux.nextArc){   // 1+n+1+n    
            Graph.comparaciones++;
            Graph.lineas++;
        
            Graph.comparaciones++;
            Graph.lineas++;
            if(  aux.getDestination() == vertex ){ //n
                Graph.lineas++;
                return aux; //1
            }
        }
        Graph.lineas++;
        return null; //1
    }
}
