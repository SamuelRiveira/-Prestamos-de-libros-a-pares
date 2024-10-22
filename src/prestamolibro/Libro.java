package prestamolibro;

public class Libro {
    private final int id; // Identificador del libro (ISBN)
    private boolean prestado;

    public Libro(int id) {
        this.id = id;
        this.prestado = false;
    }

    public int getId() {
        return id;
    }

    public synchronized boolean estaPrestado() {
        return prestado;
    }

    public synchronized void prestar() {
        this.prestado = true;
    }

    public synchronized void devolver() {
        this.prestado = false;
    }
}


