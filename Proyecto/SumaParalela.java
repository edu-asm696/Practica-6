import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// Clase que contiene la suma total
class Acumulador {
    private long total = 0;

    // Método sincronizado para evitar condiciones de carrera [cite: 52]
    public synchronized void agregar(long subtotal) {
        this.total += subtotal;
    }

    public long getTotal() {
        return total;
    }
    
    public void reset() {
        this.total = 0;
    }
}

// El hilo trabajador [cite: 8]
class HiloSuma implements Runnable {
    private int inicio;
    private int fin;
    private Acumulador acumulador;

    public HiloSuma(int inicio, int fin, Acumulador acumulador) {
        this.inicio = inicio;
        this.fin = fin;
        this.acumulador = acumulador;
    }

    @Override
    public void run() {
        long sumaParcial = 0;
        // Calculamos la sumatoria para el rango asignado: f(i) = i^2 + 3i + 1
        for (int i = inicio; i <= fin; i++) {
            // Hacemos cast a (long) para evitar desbordamiento en la multiplicación
            sumaParcial += ((long)i * i) + (3L * i) + 1;
        }
        
        // Al terminar sumamos nuestro resultado al total de forma segura [cite: 51]
        acumulador.agregar(sumaParcial);
    }
}

public class SumaParalela {
    // Definimos el límite superior (1,000,000 según la rúbrica )
    private static final int LIMITE = 1000000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numHilos = 0;

        // Validación de entrada 
        while (true) {
            try {
                System.out.print("Ingresa el número de hilos (N) a utilizar: ");
                String entrada = scanner.nextLine();
                numHilos = Integer.parseInt(entrada);
                if (numHilos > 0 && numHilos <= LIMITE) {
                    break;
                } else {
                    System.out.println("Por favor ingresa un número mayor a 0 y menor al límite.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debes ingresar un número entero.");
            }
        }

        System.out.println("\n--- Iniciando Práctica 06 ---");
        System.out.println("Calculando suma desde 1 hasta " + LIMITE);

        // 1. Ejecución con 1 Hilo (Serial) para comparación [cite: 58]
        System.out.println("\n>>> Ejecutando con 1 hilo (Base)...");
        long tiempoSerial = ejecutarSuma(1);

        // 2. Ejecución con N Hilos (Paralelo) [cite: 59]
        System.out.println("\n>>> Ejecutando con " + numHilos + " hilos...");
        long tiempoParalelo = ejecutarSuma(numHilos);

        // 3. Resultados y comparación
        System.out.println("\n--- Resultados de Tiempos ---");
        System.out.println("Tiempo con 1 hilo:  " + tiempoSerial + " ns");
        System.out.println("Tiempo con " + numHilos + " hilos: " + tiempoParalelo + " ns");
        
        if (tiempoParalelo < tiempoSerial) {
            double aceleracion = (double) tiempoSerial / tiempoParalelo;
            System.out.printf("¡Hubo aceleración! El programa fue %.2f veces más rápido.\n", aceleracion);
        } else {
            System.out.println("No hubo aceleración (esto es normal si N es muy grande para una tarea pequeña o por el overhead).");
        }
        
        scanner.close();
    }

    //Método auxiliar para ejecutar la lógica de hilos y medir tiempo.
     
    public static long ejecutarSuma(int n) {
        Acumulador acumulador = new Acumulador();
        List<Thread> hilos = new ArrayList<>();
        
        // División del trabajo [cite: 42, 44]
        int rango = LIMITE / n;
        int resto = LIMITE % n;
        int inicioActual = 1;

        long tiempoInicio = System.nanoTime(); // Inicio cronómetro [cite: 77]

        for (int i = 0; i < n; i++) {
            // Calcular fin del rango para este hilo
            int finActual = inicioActual + rango - 1;
            
            // El último hilo se lleva el resto si la división no es exacta [cite: 4]
            if (i == n - 1) {
                finActual += resto;
            }

            HiloSuma tarea = new HiloSuma(inicioActual, finActual, acumulador);
            Thread hilo = new Thread(tarea);
            hilos.add(hilo);
            hilo.start();

            // Actualizar inicio para el siguiente hilo
            inicioActual = finActual + 1;
        }

        // Esperar a que todos los hilos terminen (join) [cite: 10, 54]
        for (Thread h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long tiempoFin = System.nanoTime(); // Fin cronómetro
        long duracion = tiempoFin - tiempoInicio;

        System.out.println("Suma Total calculada: " + acumulador.getTotal());
        return duracion;
    }
}