module ni.edu.uam.practicahilos {
    requires javafx.controls;
    requires javafx.fxml;

    // Permitir que JavaFX (FXML) acceda al controlador y clases de la app
    opens ni.edu.uam.practicahilos.app to javafx.fxml;

    // Exportar los subpaquetes que contienen tus clases
    exports ni.edu.uam.practicahilos.app;
    exports ni.edu.uam.practicahilos.model;
    exports ni.edu.uam.practicahilos.tareas;
}