package ni.edu.uam.sistemas_clientes.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NavegacionService {

    private NavegacionService() {
    }

    public static void cambiarVentana(
            Stage stage,
            String archivoFXML,
            String titulo
    ) throws IOException {

        URL recurso = NavegacionService.class.getResource(
                "/ni/edu/uam/sistemas_clientes/" + archivoFXML
        );

        if (recurso == null) {
            throw new IOException(
                    "No se encontró el archivo FXML: " + archivoFXML
            );
        }

        FXMLLoader loader = new FXMLLoader(recurso);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
