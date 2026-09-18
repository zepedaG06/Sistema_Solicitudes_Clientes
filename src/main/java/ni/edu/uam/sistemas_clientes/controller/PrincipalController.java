package ni.edu.uam.sistemas_clientes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import ni.edu.uam.sistemas_clientes.service.ArchivoService;
import ni.edu.uam.sistemas_clientes.service.DatosCompartidos;
import ni.edu.uam.sistemas_clientes.service.NavegacionService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class PrincipalController {

    @FXML
    private ListView<String> listaOpciones;

    @FXML
    private void seleccionarOpcion(MouseEvent event) {

        if (event.getClickCount() != 1) {
            return;
        }

        switch (listaOpciones.getSelectionModel().getSelectedIndex()) {
            case 0 -> abrirRegistro(null);
            case 1 -> abrirConsulta(null);
            case 2 -> exportarClientes(null);
            default -> {
                // No se seleccionó una opción válida.
            }
        }
    }

    @FXML
    private void abrirRegistro(ActionEvent event) {

        try {

            NavegacionService.cambiarVentana(
                    obtenerStage(),
                    "registro-cliente.fxml",
                    "Registro de Cliente"
            );

        } catch (IOException e) {

            mostrarError(
                    "No se pudo abrir el registro de clientes."
            );
        }
    }

    @FXML
    private void abrirConsulta(ActionEvent event) {

        try {

            NavegacionService.cambiarVentana(
                    obtenerStage(),
                    "consulta-cliente.fxml",
                    "Consulta de Clientes"
            );

        } catch (IOException e) {

            mostrarError(
                    "No se pudo abrir la consulta de clientes."
            );
        }
    }

    @FXML
    private void exportarClientes(ActionEvent event) {

        if (DatosCompartidos.getClientes().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Exportar");
            alert.setHeaderText(null);
            alert.setContentText(
                    "No existen clientes registrados para exportar."
            );

            alert.showAndWait();

            return;
        }

        DirectoryChooser directoryChooser =
                new DirectoryChooser();

        directoryChooser.setTitle(
                "Seleccione la carpeta de destino"
        );

        File carpeta = directoryChooser.showDialog(
                obtenerStage()
        );

        if (carpeta == null) {
            return;
        }

        try {

            ArchivoService.exportarClientes(
                    DatosCompartidos.getClientes(),
                    carpeta.toPath()
            );

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION
            );

            alert.setTitle("Exportación");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Archivo guardado correctamente en:\n"
                            + carpeta.toPath().resolve("clientes.txt")
            );

            alert.showAndWait();

        } catch (IOException e) {

            mostrarError(
                    "No se pudo guardar el archivo."
            );
        }
    }

    @FXML
    private void mostrarDialogo(ActionEvent event) {

        Dialog<String> dialog = new Dialog<>();

        dialog.setTitle("Información del sistema");

        dialog.setHeaderText(
                "Sistema de Solicitudes de Clientes"
        );

        Label contenido = new Label(
                "Aplicación desarrollada con JavaFX.\n\n"
                        + "Permite registrar y consultar clientes."
        );

        dialog.getDialogPane().setContent(contenido);

        dialog.getDialogPane()
                .getButtonTypes()
                .add(ButtonType.OK);

        dialog.showAndWait();
    }

    @FXML
    private void accionContextual(ActionEvent event) {

        int cantidad =
                DatosCompartidos.getClientes().size();

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Clientes registrados");
        alert.setHeaderText(null);

        alert.setContentText(
                "Actualmente existen "
                        + cantidad
                        + " clientes registrados."
        );

        alert.showAndWait();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        alert.setTitle("Cerrar sesión");
        alert.setHeaderText(null);
        alert.setContentText(
                "¿Desea regresar al inicio de sesión?"
        );

        Optional<ButtonType> respuesta =
                alert.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            try {

                NavegacionService.cambiarVentana(
                        obtenerStage(),
                        "login.fxml",
                        "Inicio de Sesión"
                );

            } catch (IOException e) {

                mostrarError(
                        "No se pudo regresar al inicio de sesión."
                );
            }
        }
    }

    private Stage obtenerStage() {

        return Window
                .getWindows()
                .stream()
                .filter(window -> window instanceof Stage && window.isShowing())
                .map(window -> (Stage) window)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró la ventana principal visible."
                ));
    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}
