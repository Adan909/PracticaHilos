package ni.edu.uam.practicahilos.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Estación 3: Recurso compartido que gestiona el total de operaciones[cite: 1, 5].
 * Garantiza acceso seguro multihilo (Thread-Safety) para evitar condiciones de carrera[cite: 5].
 */
public class ContadorCompartido {
    private final AtomicInteger total = new AtomicInteger(0);

    /**
     * Incrementa el contador de forma atómica y retorna el nuevo valor.
     * @return total de operaciones actualizado tras el incremento.
     */
    public int incrementar() {
        return total.incrementAndGet();
    }

    /**
     * Retorna el total actual de forma segura[cite: 5].
     * @return total de operaciones realizadas[cite: 5].
     */
    public int getTotal() {
        return total.get();
    }

    /**
     * Reinicia el contador a cero para nuevas sesiones[cite: 5].
     */
    public void reiniciar() {
        total.set(0);
    }
}