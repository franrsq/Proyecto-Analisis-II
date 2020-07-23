package proyectoanalisisii.graph;

import java.util.ArrayList;
import java.util.Random;

/**
 * Una clase que representa un grafo interconectado por varios {@link Arc}.
 */
public class Graph {

    // Algunas variables utilizadas
    public Vertex firstVertex;
    public Vertex lastVertex;
    
    private int total;

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
        this.total=amount;
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
        System.out.println("--Voraz--");
        Vertex aux = this.firstVertex;
        Arc auxArc;
        int weight =0;
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
                weight += less.getWeight();
                aux = less.getDestination();
                if(aux == this.lastVertex){
                    System.out.println(aux.getNumber()+" Peso:"+weight);
                    this.clearMarks();
                    break;
                }
                continue;
            }
            break;
        }
    }
    
    public void genetic(){
         System.out.println("--Genetico--");
        Random random = new Random();
        int mid = this.total >>>1;
  
        // Genera Rutas Aleatorias.
        ArrayList<ArrayList<Vertex>> randomRoutes = new ArrayList<>();// un cuarto de la cantidad total de vertices
        for(int i = 0;i < mid >>> 1; i++){
            ArrayList<Vertex> randomRoute = new ArrayList<>();
            randomRoute.add(firstVertex);
            for(int l = 1; l < mid-1; l++){
                int randomPosition = random.nextInt(this.total);
                Vertex randomVertex = this.getVertexInPosition(randomPosition);
                if(randomRoute.contains(randomVertex) || randomVertex == null)
                    l--;
                else
                    randomRoute.add(randomVertex);
            }
            randomRoute.add(lastVertex);
            randomRoutes.add(randomRoute);
        }
        
        ArrayList<Vertex> lightRoute=randomRoutes.get(0);
        int lightRouteWeight = this.getWeightFromRoute(lightRoute);
        // hace las mezclas
        for(int i = 0; i < 4; i++){
            for(int index =0; index < randomRoutes.size(); index++){
                ArrayList<Vertex> route = randomRoutes.get(index);
                int max = (route.size() >>> 2)-1;
                int min = random.nextInt(route.size()-max)+1;
                
                int y = max;
                for(int x = min; x < min+(max >>> 1); x++){
                    Vertex vertex1 = route.get(x);
                    Vertex vertex2 = route.get(y);
                    
                    route.set(x, vertex2);
                    route.set(y, vertex1);
                    y--;
                }
                int weight = this.getWeightFromRoute(route);
                if( weight < lightRouteWeight){
                    lightRoute = route;
                    lightRouteWeight = weight;
                }
                
            }
        }
        printRoute(lightRoute,lightRouteWeight);
        
    }  
    
    /**
     * Recorre la ruta que se le pasa y devuelve el pso de dicha ruta.
     * @param route ruta para recorrer
     * @return entero con el peso de la ruta.
     */
    private int getWeightFromRoute(ArrayList<Vertex> route){
        int weight = 0;
        for(int i = 0; i< route.size()-1; i++){
            weight += route.get(i).getArcToVertex(route.get(i+1)).getWeight();
        }
        return weight;
    }
    
    private void printRoute(ArrayList<Vertex> route,int weight){
        route.forEach(vertex ->{
            System.out.print(vertex.getNumber()+"->");
        });
        System.out.println(" Peso:"+weight);
    }
   
    
    public Vertex getVertexInPosition(int index){
        if(index == 0 || index >= this.total-2)
            return null;
        Vertex aux = this.firstVertex;
        for( int i = 0; i<= index; i++ ){
            aux = aux.nextVertex;
        } 
        return aux;
    }
    
}
