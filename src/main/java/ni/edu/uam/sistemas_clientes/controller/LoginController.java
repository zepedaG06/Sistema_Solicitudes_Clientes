package ni.edu.uam.sistemas_clientes.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ni.edu.uam.sistemas_clientes.service.NavegacionService;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private void iniciarSesion(ActionEvent event) {
        validarLogin();
    }

    private void validarLogin() {

        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar el usuario y la contraseña.");
            alert.showAndWait();

            return;
        }

        try {

            Stage stage = (Stage) txtUsuario.getScene().getWindow();

            NavegacionService.cambiarVentana(
                    stage,
                    "principal.fxml",
                    "Sistema de Solicitudes de Clientes"
            );

        } catch (IOException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo abrir la ventana principal.");
            alert.showAndWait();
        }
    }

    @FXML
    private void teclaPresionada(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            validarLogin();
        }
    }

    @FXML
    private void salir(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Salir");
        alert.setHeaderText("Cerrar aplicación");
        alert.setContentText(
                "¿Está seguro de que desea salir del sistema?"
        );

        Optional<ButtonType> respuesta = alert.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            Platform.exit();
        }
    }
}
