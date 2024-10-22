package prestamolibro;

public class PrestamoLibro {

    public static void main(String[] args) {
        // Crear 9 libros con ISBN del 1 al 9
        Libro[] libros = new Libro[9];
        for (int i = 0; i < libros.length; i++) {
            libros[i] = new Libro(i + 1);
        }

        // Crear 4 estudiantes y comenzar sus hilos
        for (int i = 0; i < 4; i++) {
            new Thread(new Estudiante(i + 1, libros)).start();
        }
    }
    
}
