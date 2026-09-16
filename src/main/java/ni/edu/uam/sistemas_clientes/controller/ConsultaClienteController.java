package ni.edu.uam.sistemasolicitudesclientes.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ni.edu.uam.sistemasolicitudesclientes.model.Cliente;
import ni.edu.uam.sistemasolicitudesclientes.service.DatosCompartidos;
import ni.edu.uam.sistemasolicitudesclientes.service.NavegacionService;

import java.io.IOException;
import java.time.LocalDate;

public class ConsultaClienteController {

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String>
            colNombreCompleto;

    @FXML
    private TableColumn<Cliente, String>
            colTipoCliente;

    @FXML
    private TableColumn<Cliente, String>
            colCiudad;

    @FXML
    private TableColumn<Cliente, LocalDate>
            colFechaNacimiento;

    @FXML
    private TableColumn<Cliente, String>
            colTipoSolicitud;

    @FXML
    public void initialize() {

        colNombreCompleto.setCellValueFactory(
                dato ->
                        new SimpleStringProperty(
                                dato.getValue()
                                        .getNombreCompleto()
                        )
        );

        colTipoCliente.setCellValueFactory(
                new PropertyValueFactory<>(
                        "tipoCliente"
                )
        );

        colCiudad.setCellValueFactory(
                new PropertyValueFactory<>(
                        "ciudad"
                )
        );

        colFechaNacimiento.setCellValueFactory(
                new PropertyValueFactory<>(
                        "fechaNacimiento"
                )
        );

        colTipoSolicitud.setCellValueFactory(
                new PropertyValueFactory<>(
                        "tipoSolicitud"
                )
        );

        tablaClientes.setItems(
                DatosCompartidos.getClientes()
        );
    }

    @FXML
    private void dobleClicCliente(
            MouseEvent event) {

        if (event.getClickCount() == 2) {

            Cliente cliente =
                    tablaClientes
                            .getSelectionModel()
                            .getSelectedItem();

            if (cliente != null) {
                abrirDetalle(cliente);
            }
        }
    }

    private void abrirDetalle(
            Cliente cliente) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ni/edu/uam/sistemasolicitudesclientes/detalle-cliente.fxml"
                            )
                    );

            Parent root = loader.load();

            DetalleClienteController controller =
                    loader.getController();

            controller.setCliente(cliente);

            Stage stage =
                    (Stage)
                            tablaClientes
                                    .getScene()
                                    .getWindow();

            Scene scene = new Scene(root);

            stage.setTitle(
                    "Detalle del Cliente"
            );

            stage.setScene(scene);

        } catch (IOException e) {

            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR
                    );

            alert.setTitle("Error");
            alert.setHeaderText(null);

            alert.setContentText(
                    "No se pudo abrir el detalle del cliente."
            );

            alert.showAndWait();
        }
    }

    @FXML
    private void volverPrincipal(
            ActionEvent event) {

        try {

            Stage stage =
                    (Stage)
                            tablaClientes
                                    .getScene()
                                    .getWindow();

            NavegacionService.cambiarVentana(
                    stage,
                    "principal.fxml",
                    "Sistema de Solicitudes de Clientes"
            );

        } catch (IOException e) {

            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR
                    );

            alert.setContentText(
                    "No se pudo regresar al menú principal."
            );

            alert.showAndWait();
        }
    }
}