package ni.edu.uam.sistemas_clientes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SistemaSolicitudesApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        URL recurso = SistemaSolicitudesApplication.class.getResource(
                "/ni/edu/uam/sistemas_clientes/login.fxml"
        );

        if (recurso == null) {
            throw new IOException("No se encontró login.fxml");
        }

        FXMLLoader loader = new FXMLLoader(recurso);
        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema de Solicitudes de Clientes");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
