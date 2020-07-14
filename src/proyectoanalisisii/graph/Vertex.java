package proyectoanalisisii.graph;

/**
 * Representa los vertices de un {@link Graph}.
 */
public class Vertex {

    public Vertex nextVertex;
    public Arc firstArc;
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

    public void setNumber(int number) {
        this.number = number;
    }
}
