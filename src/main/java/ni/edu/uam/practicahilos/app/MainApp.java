package ni.edu.uam.practicahilos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // La barra '/' inicial busca desde la raíz de la carpeta resources
        URL fxmlLocation = getClass().getResource("/ni/edu/uam/practicahilos/ventana_principal.fxml");

        if (fxmlLocation == null) {
            throw new IllegalStateException("No se pudo encontrar el archivo FXML en la ruta especificada.");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Sistema de Ventanillas Concurrente");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}