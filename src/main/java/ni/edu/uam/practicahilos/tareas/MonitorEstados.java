package ni.edu.uam.practicahilos.tareas;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Estación 2: Monitor encargado de consultar periódicamente el estado (Thread.State)[cite: 1, 3]
 * de los hilos de trabajo sin bloquear la interfaz gráfica[cite: 1, 3].
 */
public class MonitorEstados implements Runnable {
    private final Map<String, Thread> hilosAMonitorear;
    private final Consumer<String> callbackReporte;
    private volatile boolean monitoreando = true;

    public MonitorEstados(Map<String, Thread> hilosAMonitorear, Consumer<String> callbackReporte) {
        this.hilosAMonitorear = hilosAMonitorear;
        this.callbackReporte = callbackReporte;
    }

    public void detener() {
        this.monitoreando = false;
    }

    @Override
    public void run() {
        while (monitoreando && !Thread.currentThread().isInterrupted()) {
            StringBuilder reporte = new StringBuilder("--- Estado de Hilos ---\n");
            boolean todosTerminados = true;

            for (Map.Entry<String, Thread> entry : hilosAMonitorear.entrySet()) {
                Thread.State estado = entry.getValue().getState();
                reporte.append(entry.getKey()).append(": ").append(estado).append("\n");

                if (estado != Thread.State.TERMINATED) {
                    todosTerminados = false;
                }
            }

            if (callbackReporte != null) {
                callbackReporte.accept(reporte.toString());
            }

            if (todosTerminados) {
                break;
            }

            try {
                Thread.sleep(200); // Muestreo cada 200 ms[cite: 3]
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}