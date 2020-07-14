package proyectoanalisisii.graph;

/**
 * Representa los arcos que conectan los múltiples {@link Vertex} que tiene
 * {@link Graph}.
 */
public class Arc {

    public Arc nextArc;

    private Vertex destination;
    private int weight; // Peso del arco, numero aleatorio de 1 a 99

    /**
     * Constructor por defecto.
     *
     * @param destination {@link Vertex} destino
     */
    public Arc(Vertex destination) {
        this.destination = destination;

        weight = (int) (Math.random() * 99 + 1);
    }

    // Getters and setters
    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
