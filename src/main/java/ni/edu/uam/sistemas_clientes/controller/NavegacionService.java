package ni.edu.uam.sistemasolicitudesclientes.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavegacionService {

    public static void cambiarVentana(
            Stage stage,
            String archivoFXML,
            String titulo)
            throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        NavegacionService.class
                                .getResource(
                                        "/ni/edu/uam/sistemasolicitudesclientes/"
                                                + archivoFXML
                                )
                );

        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}