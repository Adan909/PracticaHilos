package ni.edu.uam.practicahilos.tareas;

import ni.edu.uam.practicahilos.model.ContadorCompartido;
import java.util.function.BiConsumer;

/**
 * Estaciones 1 y 4: Tarea ejecutable en segundo plano que representa una ventanilla[cite: 1, 4].
 * Implementa Runnable y soporte para cancelación cooperativa[cite: 1, 4].
 */
public class ProcesoVentanilla implements Runnable {
    private final String nombre;
    private final ContadorCompartido contador;
    private final BiConsumer<String, Integer> callbackProgreso;
    private volatile boolean activo = true;

    public ProcesoVentanilla(String nombre, ContadorCompartido contador, BiConsumer<String, Integer> callbackProgreso) {
        this.nombre = nombre;
        this.contador = contador;
        this.callbackProgreso = callbackProgreso;
    }

    /**
     * Solicita la detención cooperativa del hilo cambiando la bandera volatile[cite: 4].
     */
    public void detener() {
        this.activo = false;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5 && activo && !Thread.currentThread().isInterrupted(); i++) {
            // Incrementar recurso compartido de forma segura
            contador.incrementar();
            int totalActual = contador.getTotal();

            // Notificar progreso a la interfaz mediante callback[cite: 4]
            if (callbackProgreso != null) {
                callbackProgreso.accept(nombre + " procesó la solicitud #" + i, totalActual);
            }

            try {
                // Simulación de procesamiento de 500ms[cite: 4]
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Preservar el estado de interrupción del hilo[cite: 4]
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public String getNombre() {
        return nombre;
    }
}