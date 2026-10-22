package ni.edu.uam.practicahilos.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import ni.edu.uam.practicahilos.model.ContadorCompartido;
import ni.edu.uam.practicahilos.tareas.ProcesoVentanilla;

public class VentanillaController {

    @FXML private Label etiquetaEstadoA;
    @FXML private Label etiquetaEstadoB;
    @FXML private Label etiquetaContador;
    @FXML private Button btnIniciar;
    @FXML private Button btnDetener;
    @FXML private TextArea areaRegistro;

    private ContadorCompartido contador;
    private ProcesoVentanilla ventanillaA;
    private ProcesoVentanilla ventanillaB;
    private Thread hiloA;
    private Thread hiloB;
    private Timeline monitorUI;

    public VentanillaController() {
    }

    @FXML
    public void initialize() {
        contador = new ContadorCompartido();
        btnDetener.setDisable(true);

        // Monitor periódico para refrescar la interfaz
        monitorUI = new Timeline(new KeyFrame(Duration.millis(100), e -> actualizarInterfaz()));
        monitorUI.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void iniciarProceso() {
        btnIniciar.setDisable(true);
        btnDetener.setDisable(false);
        contador.reiniciar();
        registrarMensaje("Iniciando procesamiento...");

        // AQUÍ SE AGREGAN LOS CALLBACKS DE PROGRESO PASO A PASO
        ventanillaA = new ProcesoVentanilla("Ventanilla A", contador, (msg, total) ->
                Platform.runLater(() -> registrarMensaje(msg + " | Total acumulado: " + total))
        );

        ventanillaB = new ProcesoVentanilla("Ventanilla B", contador, (msg, total) ->
                Platform.runLater(() -> registrarMensaje(msg + " | Total acumulado: " + total))
        );

        hiloA = new Thread(ventanillaA, "Ventanilla-A");
        hiloB = new Thread(ventanillaB, "Ventanilla-B");

        hiloA.start();
        hiloB.start();

        monitorUI.play();
    }

    @FXML
    private void detenerProceso() {
        registrarMensaje("Solicitando detención cooperativa...");
        btnDetener.setDisable(true);

        if (ventanillaA != null) ventanillaA.detener();
        if (ventanillaB != null) ventanillaB.detener();

        if (hiloA != null) hiloA.interrupt();
        if (hiloB != null) hiloB.interrupt();
    }

    private void actualizarInterfaz() {
        if (ventanillaA != null && ventanillaB != null) {
            etiquetaEstadoA.setText("Estado A: " + ventanillaA.getEstado());
            etiquetaEstadoB.setText("Estado B: " + ventanillaB.getEstado());
            etiquetaContador.setText(String.valueOf(contador.getTotal()));

            if (!hiloA.isAlive() && !hiloB.isAlive()) {
                monitorUI.stop();
                btnIniciar.setDisable(false);
                registrarMensaje("Hilos finalizados. Listo para nuevo proceso.");
            }
        }
    }

    private void registrarMensaje(String mensaje) {
        areaRegistro.appendText(mensaje + "\n");
    }
}