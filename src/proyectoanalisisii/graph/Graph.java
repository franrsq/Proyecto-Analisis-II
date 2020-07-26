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
    
    public static int asignaciones,comparaciones,lineas;

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
     * 
     * Es linea de formula 3n+2
     */
    private void clearMarks() {
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
    
    public void printVars(float time) {
        System.out.print("Timepo transcurrido: ");
        System.out.printf("Tiempo = %.3f S\n", time / 1000);
        System.out.print("Comparaciones: ");
        System.out.println(comparaciones);
        System.out.print("Asignaciones: ");
        System.out.println(asignaciones);
        System.out.print("Lineas de codigo: ");
        System.out.println(lineas);
        System.out.println("\n");
        comparaciones = 0;
        asignaciones = 0;
        lineas = 0;
    }
    
     /**
     * Algoritmo voraz.
     * 
     * Formula: 5n[2]+14n+8.
     * Memoria: 
     */
    public void voracious(){
        System.out.println("--Voraz--");
        lineas++;
        asignaciones++;
        Vertex aux = this.firstVertex; //1
        Arc auxArc;
        
        lineas++;
        asignaciones++;
        int weight =0; //1
        lineas++;
        while (true) {  //n
            //busca el primer arco libre y de menor peso 
            lineas++;
            asignaciones++;
            Arc less = null; // n
            
            lineas++;
            asignaciones++;
            comparaciones++;
            for(auxArc = aux.firstArc; auxArc != null;auxArc = auxArc.nextArc){ // n(n+3)
                comparaciones++;
                lineas +=2;
                
                lineas +=2;
                comparaciones++;
                if(!auxArc.getDestination().mark){// n(n)
                    lineas ++;
                    comparaciones++;
                    if(less == null){ // n(n)
                        lineas++;
                        asignaciones++;
                        less = auxArc; // 1
                        lineas++;
                        continue; // 1
                    }
                    lineas++;
                    comparaciones++;
                    if(auxArc.getWeight() < less.getWeight()){ // n(n)
                        lineas++;
                        asignaciones++;
                        less = auxArc; // n(n)
                    }
                }
            }     
            
            lineas++;
            comparaciones++;
            if(less != null){ // n
                lineas++;
                asignaciones++;
                aux.mark =true; // n
                
                System.out.print(aux.getNumber()+"->");
                
                lineas++;
                asignaciones++;
                weight += less.getWeight(); // n
                
                lineas++;
                asignaciones++;
                aux = less.getDestination(); // n
                
                comparaciones++;
                lineas++;
                if(aux == this.lastVertex){ // n
                    System.out.println(aux.getNumber()+" Peso:"+weight);
      
                    this.clearMarks(); // 3n+2
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
    
    /**
     * Algoritmo genetico de ruta corta
     * Fomrula (483n[3]+1722n[2]+2695n+644)/16 <-- esta malo 
     */
    public void genetic(){
        lineas++;
        asignaciones++;
        Random random = new Random();// 1
        lineas++;
        asignaciones++;
        int mid = this.total >>>1; // 1

        // Genera Rutas Aleatorias.
        lineas++;
        asignaciones++;
        ArrayList<ArrayList<Vertex>> randomRoutes = new ArrayList<>(); //  1

        // (3n[3]+2n[2]+23n+116)/16
        lineas++;
        asignaciones++;
        for(int i = 0;i < mid >>> 1; i++){ // (2n+2)/4
            lineas++;
            comparaciones++;
            
            lineas++;
            asignaciones++;
            ArrayList<Vertex> randomRoute = new ArrayList<>(); // n/4
            
            lineas++;
            asignaciones++;
            randomRoute.add(firstVertex);  // n/4
            
            lineas++;
            asignaciones++;
            for(int l = 1; l < mid-1; l++){ // 1 + n/4(n-1/4) + (n-1)/4
                lineas++;
                comparaciones++;   
                
                lineas++;
                asignaciones++;    
                int randomPosition = random.nextInt(this.total); // (n-1/4)(n/4) 
                
                lineas++;
                asignaciones++;   
                Vertex randomVertex = this.getVertexInPosition(randomPosition); // (n-1/4)(n/4)(3n+6)
                
                lineas++;
                comparaciones+=2;   
                if(randomRoute.contains(randomVertex) || randomVertex == null){// (n-1/4)(n/4)
                    lineas++;
                    asignaciones++;
                    l--; // (n-1/4)(n/4)
                }else{
                    lineas++;
                    asignaciones++;
                    randomRoute.add(randomVertex); // (n-1/4)(n/4)
                }
            }
            lineas++;
            asignaciones++;
            randomRoute.add(lastVertex); // n/4
            
            lineas++;
            asignaciones++;
            randomRoutes.add(randomRoute); // n/4
        }

        lineas++;
        asignaciones++;
        ArrayList<Vertex> lightRoute=randomRoutes.get(0); // 1
        
        lineas++;
        asignaciones++;
        int lightRouteWeight = this.getWeightFromRoute(lightRoute); // 3n[2]+9n+4
        // 2(15+66n+(5n[2]+10n)/2 +(25x[2]+50x)/4 +15n[3] +45n[2])
        
        lineas++;
        asignaciones++;
        for(int i = 0; i < 4; i++){ // 10
            lineas++;
            comparaciones++;
                
            lineas++;
            asignaciones++;
            for(int index =0; index < randomRoutes.size(); index++){ // 10(1+n+1+n)
                lineas++;
                comparaciones++;
            
                lineas++;
                asignaciones++;
                ArrayList<Vertex> route = randomRoutes.get(index); // 10n
                
                lineas++;
                asignaciones++;
                int max = (route.size() >>> 2)-1;  // 10n                      // n/2-1
                
                lineas++;
                asignaciones++;
                int min = random.nextInt(route.size()-max)+1;  // 10n          // (n-(n/2-1))+1

                lineas++;
                asignaciones++;
                int y = max;  // 10n
                
                lineas++;
                asignaciones++;
                for(int x = min; x < min+(max >>> 1); x++){  // 10n(1+ (n+2)/4+ 1 + (n+2)/4)
                    lineas++;
                    comparaciones++;
                    
                    lineas++;
                    asignaciones++;
                    Vertex vertex1 = route.get(x); // 10n(n+2)/4)
                    
                    lineas++;
                    asignaciones++;
                    Vertex vertex2 = route.get(y); // 10n(n+2)/4)

                    lineas++;
                    asignaciones++;
                    route.set(x, vertex2); // 10n(n+2)/4)
                    lineas++;
                    asignaciones++;
                    route.set(y, vertex1); // 10n(n+2)/4)
                    lineas++;
                    asignaciones++;
                    y--; // 10n(n+2)/4)
                }

                lineas++;
                asignaciones++;
                int weight = this.getWeightFromRoute(route); //10n(3n[2]+9n+4) 
                
                lineas++;
                comparaciones++;
                if( weight < lightRouteWeight){ //10n
                    lineas++;
                    asignaciones++;
                    lightRoute = route; //10n
                    
                    lineas++;
                    asignaciones++;
                    lightRouteWeight = weight; //10n
                }

            }
        }
        lineas++;
        printRoute(lightRoute,lightRouteWeight);
        
    }  
    
    /**
     * Recorre la ruta que se le pasa y devuelve el pso de dicha ruta.
     * @param route ruta para recorrer
     * @return entero con el peso de la ruta.
     * Formula: 3n[2]+9n+4
     */
    private int getWeightFromRoute(ArrayList<Vertex> route){
        lineas++;
        asignaciones++;
        int weight = 0; //1
        
        lineas++;
        asignaciones++;
        for(int i = 0; i< route.size()-1; i++){ // 1+n+1+n
            comparaciones++;
            lineas++;
            
            lineas++;
            asignaciones++;
            Vertex aux = route.get(i); //n
            
            lineas++;
            asignaciones++;
            Vertex aux2 = route.get(i +1);  //n
            
            lineas++;
            asignaciones++;
            Arc arc = aux.getArcToVertex(aux2);  //n(3n + 4)
            
            lineas+=2;
            asignaciones++;
            weight += arc.getWeight(); // n
        }
        lineas++;
        return weight; //1
    }
    
    private void printRoute(ArrayList<Vertex> route,int weight){
        route.forEach(vertex ->{
            System.out.print(vertex.getNumber()+"->");
        });
        System.out.println(" Peso:"+weight);
    }
   
   
    /**
     * Recuper el vertice en la posicion indicad
     * @param index posicion del vertice 
     * @return vetice en la posicion o NULL si no existe.
     * Formula: 3n + 6 
     */
    public Vertex getVertexInPosition(int index){
        lineas++;
        comparaciones += 2;
        if(index == 0 || index >= this.total-2){ //1
            lineas++;
            return null; //1
        }
        lineas++;
        asignaciones++;
        Vertex aux = this.firstVertex; //1
        
        lineas++;
        asignaciones++;
        for( int i = 0; i<= index; i++ ){ // 1 + n+1 +n
            comparaciones++;
            lineas++;
            
            lineas++;
            asignaciones++;
            aux = aux.nextVertex; // n
        } 
        
        lineas++;
        return aux; //1
    }
    
}
