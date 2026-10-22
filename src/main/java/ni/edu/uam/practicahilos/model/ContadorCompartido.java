package ni.edu.uam.practicahilos.model;

/**
 * Estación 3: Recurso compartido que gestiona el total de operaciones[cite: 1, 5].
 * Garantiza acceso seguro multihilo (Thread-Safety) para evitar condiciones de carrera[cite: 5].
 */
public class ContadorCompartido {
    private int total = 0;

    /**
     * Incrementa el contador de forma sincronizada[cite: 5].
     * El uso de 'synchronized' garantiza atomicidad y visibilidad entre hilos[cite: 5].
     */
    public synchronized void incrementar() {
        total++;
    }

    /**
     * Retorna el total actual de forma segura[cite: 5].
     * @return total de operaciones realizadas[cite: 5].
     */
    public synchronized int getTotal() {
        return total;
    }

    /**
     * Reinicia el contador a cero para nuevas sesiones[cite: 5].
     */
    public synchronized void reiniciar() {
        total = 0;
    }
}