package ni.edu.uam.sistemas_clientes.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.sistemas_clientes.model.Cliente;

public class DatosCompartidos {

    private static final ObservableList<Cliente> clientes =
            FXCollections.observableArrayList();

    private DatosCompartidos() {
    }

    public static ObservableList<Cliente> getClientes() {
        return clientes;
    }
}
