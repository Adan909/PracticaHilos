package ni.edu.uam.practicahilos.tareas;

import ni.edu.uam.practicahilos.model.ContadorCompartido;
import java.util.function.BiConsumer;

/**
 * Estaciones 1 y 4: Tarea ejecutable en segundo plano que representa una ventanilla[cite: 1, 4].
 * Implementa Runnable, control de estados y cancelación cooperativa[cite: 1, 4].
 */
public class ProcesoVentanilla implements Runnable {
    private final String nombre;
    private final ContadorCompartido contador;
    private final BiConsumer<String, Integer> callbackProgreso;
    private volatile boolean activo = true;
    private volatile String estado = "En espera";

    // 1. CONSTRUCTOR DE 2 PARÁMETROS (Resuelve el error "Expected 3 arguments but found 2")
    public ProcesoVentanilla(String nombre, ContadorCompartido contador) {
        this(nombre, contador, null);
    }

    // Constructor completo de 3 parámetros
    public ProcesoVentanilla(String nombre, ContadorCompartido contador, BiConsumer<String, Integer> callbackProgreso) {
        this.nombre = nombre;
        this.contador = contador;
        this.callbackProgreso = callbackProgreso;
    }

    /**
     * Solicita la detención cooperativa del hilo cambiando la bandera y el estado[cite: 4].
     */
    public void detener() {
        this.activo = false;
        this.estado = "Deteniendo";
    }

    // 2. MÉTODO getEstado() (Resuelve el error "Cannot resolve method 'getEstado'")
    public String getEstado() {
        return estado;
    }

    @Override
    public void run() {
        this.estado = "Ejecutando";
        for (int i = 1; i <= 5 && activo && !Thread.currentThread().isInterrupted(); i++) {
            // Incrementar recurso compartido de forma atómica capturando el valor exacto
            int totalActual = contador.incrementar();

            // Notificar progreso a través del callback si existe
            if (callbackProgreso != null) {
                callbackProgreso.accept(nombre + " procesó la solicitud #" + i, totalActual);
            }

            try {
                // Simulación de procesamiento de 500ms[cite: 4]
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Preservar el estado de interrupción para salir de forma segura[cite: 4]
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Asignar el estado final según la causa de finalización
        if (!activo || Thread.currentThread().isInterrupted()) {
            this.estado = "Detenido";
        } else {
            this.estado = "Finalizado";
        }
    }

    public String getNombre() {
        return nombre;
    }
}