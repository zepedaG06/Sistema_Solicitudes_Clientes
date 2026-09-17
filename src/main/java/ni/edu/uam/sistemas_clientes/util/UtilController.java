package ni.edu.uam.sistemas_clientes.util;

import javafx.scene.control.Alert;

public class UtilController {

    private UtilController() {
    }

    public static void mostrarMensaje(
            Alert.AlertType alertType,
            String titulo,
            String mensaje
    ) {

        Alert alert = new Alert(alertType);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}
