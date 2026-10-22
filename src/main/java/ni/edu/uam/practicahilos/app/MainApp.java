package ni.edu.uam.practicahilos.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ni/edu/uam/practicahilos/vista.fxml"));
        Parent raiz = loader.load();

        Scene escena = new Scene(raiz);
        escenarioPrincipal.setTitle("Laboratorio Concurrencia - Ventanillas");
        escenarioPrincipal.setScene(escena);

        escenarioPrincipal.setOnCloseRequest(evento -> {
            Platform.exit();
            System.exit(0);
        });

        escenarioPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
