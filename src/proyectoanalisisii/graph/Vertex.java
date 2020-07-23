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
        if(this.totalArcs-1 < 0 || index > this.totalArcs)
            return null;
        Arc aux = this.firstArc;
        for( int i = 0; i<= index; i++ ){
            aux = aux.nextArc;
        } 
        return aux;
    }
    
    /**
     * Busca el arco que apunta al vertices espcificado
     * @param vertex vertice que apunta el arco 
     * @return Arc si se encunetra el arco o Null si no se encuntra .
     */
    public Arc getArcToVertex(Vertex vertex){
        for( Arc aux = this.firstArc; aux != null; aux = aux.nextArc){       
            if(  aux.getDestination() == vertex ){
                return aux;
            }
        }
        return null;
    }
}
