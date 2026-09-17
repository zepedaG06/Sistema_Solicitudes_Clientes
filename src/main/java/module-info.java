module ni.edu.uam.sistemas_clientes {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.sistemas_clientes to javafx.fxml;
    opens ni.edu.uam.sistemas_clientes.controller to javafx.fxml;
    exports ni.edu.uam.sistemas_clientes;
    exports ni.edu.uam.sistemas_clientes.model;
}
