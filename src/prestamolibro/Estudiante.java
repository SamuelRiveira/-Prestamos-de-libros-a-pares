package prestamolibro;

import java.util.Random;

public class Estudiante implements Runnable {
    private final int id;
    private final Libro[] libros;
    private final Random random = new Random();
    private final int maxPrestamos = 2;  // Máximo de dos préstamos

    public Estudiante(int id, Libro[] libros) {
        this.id = id;
        this.libros = libros;
    }

    @Override
    public void run() {
        int prestamosRealizados = 0;

        while (prestamosRealizados < maxPrestamos) {
            try {
                // Selecciona dos libros al azar
                Libro[] librosSeleccionados = obtenerLibrosAlAzar();

                // Usa los libros por un tiempo aleatorio (1 a 3 horas, convertidas a minutos)
                int tiempoUso = (random.nextInt(3) + 1) * 60;
                System.out.println("Estudiante " + id + " usando los libros " 
                    + librosSeleccionados[0].getId() + " y " + librosSeleccionados[1].getId() 
                    + " por " + tiempoUso + " minutos.");
                Thread.sleep(tiempoUso);  // Simula el tiempo de uso (1 ms por cada minuto)

                // Devuelve los libros
                devolverLibros(librosSeleccionados);

                prestamosRealizados++;  // Incrementa el contador de préstamos

                // Descansa por un tiempo aleatorio (1 a 2 horas, convertidas a minutos)
                if (prestamosRealizados < maxPrestamos) {
                    int tiempoDescanso = (random.nextInt(2) + 1) * 60;
                    System.out.println("Estudiante " + id + " descansando por " + tiempoDescanso + " minutos.");
                    Thread.sleep(tiempoDescanso);  // Simula el tiempo de descanso (1 ms por cada minuto)
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Estudiante " + id + " ha terminado después de " + prestamosRealizados + " préstamos.");
    }

    private synchronized Libro[] obtenerLibrosAlAzar() throws InterruptedException {
        Libro[] seleccionados = new Libro[2];

        // Seleccionar dos libros al azar sin repetir
        while (true) {
            int libro1Index = random.nextInt(libros.length);
            int libro2Index = random.nextInt(libros.length);

            // Asegurarse de que no se seleccione el mismo libro dos veces
            if (libro1Index != libro2Index) {
                Libro libro1 = libros[libro1Index];
                Libro libro2 = libros[libro2Index];

                // Espera si alguno de los libros ya está prestado
                if (!libro1.estaPrestado() && !libro2.estaPrestado()) {
                    libro1.prestar();
                    libro2.prestar();
                    seleccionados[0] = libro1;
                    seleccionados[1] = libro2;
                    System.out.println("Estudiante " + id + " seleccionó los libros " 
                        + libro1.getId() + " y " + libro2.getId());
                    return seleccionados;
                } else {
                    System.out.println("Estudiante " + id + " esperando por los libros " 
                        + libro1.getId() + " o " + libro2.getId());
                    wait(); // Espera a que los libros estén disponibles
                }
            }
        }
    }

    private synchronized void devolverLibros(Libro[] librosSeleccionados) {
        librosSeleccionados[0].devolver();
        librosSeleccionados[1].devolver();
        System.out.println("Estudiante " + id + " devolvió los libros " 
            + librosSeleccionados[0].getId() + " y " + librosSeleccionados[1].getId());
        notifyAll();  // Notifica a otros estudiantes que los libros están disponibles
    }
}


