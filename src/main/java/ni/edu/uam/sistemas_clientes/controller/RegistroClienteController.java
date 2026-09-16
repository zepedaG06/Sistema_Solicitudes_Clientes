package ni.edu.uam.sistemas_clientes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ni.edu.uam.sistemasolicitudesclientes.model.Cliente;
import ni.edu.uam.sistemasolicitudesclientes.service.DatosCompartidos;
import ni.edu.uam.sistemasolicitudesclientes.service.NavegacionService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.StringJoiner;

public class RegistroClienteController {

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtApellidos;

    @FXML
    private ComboBox<String> cmbTipoCliente;

    @FXML
    private ComboBox<String> cmbCiudad;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private RadioButton rbInformacion;

    @FXML
    private RadioButton rbSoporte;

    @FXML
    private RadioButton rbReclamo;

    @FXML
    private CheckBox chkInternet;

    @FXML
    private CheckBox chkTelefonia;

    @FXML
    private CheckBox chkSoporteTecnico;

    @FXML
    private ImageView imageFoto;

    private ToggleGroup grupoSolicitud;

    private String rutaFoto;

    @FXML
    public void initialize() {

        cmbTipoCliente.getItems().addAll(
                "Nuevo",
                "Frecuente",
                "Corporativo"
        );

        cmbCiudad.getItems().addAll(
                "Managua",
                "Masaya",
                "Granada",
                "León",
                "Chinandega",
                "Estelí",
                "Matagalpa",
                "Jinotega",
                "Rivas",
                "Carazo"
        );

        grupoSolicitud = new ToggleGroup();

        rbInformacion.setToggleGroup(
                grupoSolicitud
        );

        rbSoporte.setToggleGroup(
                grupoSolicitud
        );

        rbReclamo.setToggleGroup(
                grupoSolicitud
        );
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {

        FileChooser fileChooser =
                new FileChooser();

        fileChooser.setTitle(
                "Seleccionar fotografía"
        );

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imágenes",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        File archivo = fileChooser.showOpenDialog(
                obtenerStage()
        );

        if (archivo != null) {

            rutaFoto = archivo.getAbsolutePath();

            Image imagen = new Image(
                    archivo.toURI().toString()
            );

            imageFoto.setImage(imagen);
        }
    }

    @FXML
    private void guardarCliente(ActionEvent event) {

        String nombres =
                txtNombres.getText().trim();

        String apellidos =
                txtApellidos.getText().trim();

        String tipoCliente =
                cmbTipoCliente.getValue();

        String ciudad =
                cmbCiudad.getValue();

        LocalDate fechaNacimiento =
                dpFechaNacimiento.getValue();

        String tipoSolicitud =
                obtenerTipoSolicitud();

        String servicios =
                obtenerServicios();

        if (nombres.isEmpty()
                || apellidos.isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar los nombres y apellidos."
            );

            return;
        }

        if (tipoCliente == null) {

            mostrarAdvertencia(
                    "Seleccione el tipo de cliente."
            );

            return;
        }

        if (ciudad == null) {

            mostrarAdvertencia(
                    "Seleccione una ciudad."
            );

            return;
        }

        if (fechaNacimiento == null) {

            mostrarAdvertencia(
                    "Seleccione la fecha de nacimiento."
            );

            return;
        }

        if (fechaNacimiento.isAfter(
                LocalDate.now())) {

            mostrarAdvertencia(
                    "La fecha de nacimiento no puede ser futura."
            );

            return;
        }

        if (tipoSolicitud == null) {

            mostrarAdvertencia(
                    "Seleccione un tipo de solicitud."
            );

            return;
        }

        if (servicios.isEmpty()) {

            mostrarAdvertencia(
                    "Seleccione al menos un servicio de interés."
            );

            return;
        }

        Cliente cliente = new Cliente(
                nombres,
                apellidos,
                tipoCliente,
                ciudad,
                fechaNacimiento,
                tipoSolicitud,
                servicios,
                rutaFoto
        );

        DatosCompartidos
                .getClientes()
                .add(cliente);

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Registro");
        alert.setHeaderText(null);
        alert.setContentText(
                "Cliente registrado correctamente."
        );

        alert.showAndWait();

        limpiarFormulario();
    }

    private String obtenerTipoSolicitud() {

        RadioButton seleccionado =
                (RadioButton)
                        grupoSolicitud
                                .getSelectedToggle();

        if (seleccionado == null) {
            return null;
        }

        return seleccionado.getText();
    }

    private String obtenerServicios() {

        StringJoiner servicios =
                new StringJoiner(", ");

        if (chkInternet.isSelected()) {
            servicios.add("Internet");
        }

        if (chkTelefonia.isSelected()) {
            servicios.add("Telefonía");
        }

        if (chkSoporteTecnico.isSelected()) {
            servicios.add("Soporte técnico");
        }

        return servicios.toString();
    }

    @FXML
    private void limpiar(ActionEvent event) {
        limpiarFormulario();
    }

    private void limpiarFormulario() {

        txtNombres.clear();
        txtApellidos.clear();

        cmbTipoCliente
                .getSelectionModel()
                .clearSelection();

        cmbCiudad
                .getSelectionModel()
                .clearSelection();

        dpFechaNacimiento.setValue(null);

        grupoSolicitud
                .selectToggle(null);

        chkInternet.setSelected(false);
        chkTelefonia.setSelected(false);
        chkSoporteTecnico.setSelected(false);

        imageFoto.setImage(null);

        rutaFoto = null;

        txtNombres.requestFocus();
    }

    @FXML
    private void cancelar(ActionEvent event) {

        try {

            NavegacionService.cambiarVentana(
                    obtenerStage(),
                    "principal.fxml",
                    "Sistema de Solicitudes de Clientes"
            );

        } catch (IOException e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(
                    "No se pudo regresar al menú principal."
            );

            alert.showAndWait();
        }
    }

    private Stage obtenerStage() {

        return (Stage)
                txtNombres
                        .getScene()
                        .getWindow();
    }

    private void mostrarAdvertencia(
            String mensaje) {

        Alert alert = new Alert(
                Alert.AlertType.WARNING
        );

        alert.setTitle("Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}