package ni.edu.uam.sistemasolicitudesclientes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ni.edu.uam.sistemasolicitudesclientes.model.Cliente;
import ni.edu.uam.sistemasolicitudesclientes.service.NavegacionService;

import java.io.File;
import java.io.IOException;

public class DetalleClienteController {

    @FXML
    private Label lblNombres;

    @FXML
    private Label lblApellidos;

    @FXML
    private Label lblTipoCliente;

    @FXML
    private Label lblCiudad;

    @FXML
    private Label lblFechaNacimiento;

    @FXML
    private Label lblTipoSolicitud;

    @FXML
    private Label lblServicios;

    @FXML
    private ImageView imageFoto;

    private Cliente cliente;

    public void setCliente(Cliente cliente) {

        this.cliente = cliente;

        mostrarDatos();
    }

    private void mostrarDatos() {

        if (cliente == null) {
            return;
        }

        lblNombres.setText(
                cliente.getNombres()
        );

        lblApellidos.setText(
                cliente.getApellidos()
        );

        lblTipoCliente.setText(
                cliente.getTipoCliente()
        );

        lblCiudad.setText(
                cliente.getCiudad()
        );

        lblFechaNacimiento.setText(
                cliente.getFechaNacimiento()
                        .toString()
        );

        lblTipoSolicitud.setText(
                cliente.getTipoSolicitud()
        );

        lblServicios.setText(
                cliente.getServiciosInteres()
        );

        mostrarFoto();
    }

    private void mostrarFoto() {

        String rutaFoto =
                cliente.getRutaFoto();

        if (rutaFoto == null
                || rutaFoto.isEmpty()) {

            imageFoto.setImage(null);

            return;
        }

        File archivo =
                new File(rutaFoto);

        if (archivo.exists()) {

            Image imagen =
                    new Image(
                            archivo
                                    .toURI()
                                    .toString()
                    );

            imageFoto.setImage(imagen);
        }
    }

    @FXML
    private void regresar(
            ActionEvent event) {

        try {

            Stage stage =
                    (Stage)
                            lblNombres
                                    .getScene()
                                    .getWindow();

            NavegacionService.cambiarVentana(
                    stage,
                    "consulta-cliente.fxml",
                    "Consulta de Clientes"
            );

        } catch (IOException e) {

            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR
                    );

            alert.setContentText(
                    "No se pudo regresar a la consulta."
            );

            alert.showAndWait();
        }
    }
}