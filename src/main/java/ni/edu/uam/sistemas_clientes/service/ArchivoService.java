package ni.edu.uam.sistemas_clientes.service;

import ni.edu.uam.sistemas_clientes.model.Cliente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ArchivoService {

    private ArchivoService() {
    }

    public static void exportarClientes(
            List<Cliente> clientes,
            Path carpeta
    ) throws IOException {

        if (clientes == null) {
            throw new IllegalArgumentException(
                    "La lista de clientes no puede ser null."
            );
        }

        if (carpeta == null) {
            throw new IllegalArgumentException(
                    "La carpeta de destino no puede ser null."
            );
        }

        StringBuilder contenido = new StringBuilder();

        contenido.append("CLIENTES REGISTRADOS\n");
        contenido.append("====================\n\n");

        for (Cliente cliente : clientes) {
            contenido.append("Nombre: ")
                    .append(cliente.getNombreCompleto())
                    .append("\n");

            contenido.append("Tipo de cliente: ")
                    .append(cliente.getTipoCliente())
                    .append("\n");

            contenido.append("Ciudad: ")
                    .append(cliente.getCiudad())
                    .append("\n");

            contenido.append("Fecha de nacimiento: ")
                    .append(cliente.getFechaNacimiento())
                    .append("\n");

            contenido.append("Tipo de solicitud: ")
                    .append(cliente.getTipoSolicitud())
                    .append("\n");

            contenido.append("Servicios: ")
                    .append(cliente.getServiciosInteres())
                    .append("\n");

            contenido.append("------------------------\n");
        }

        Path archivo = carpeta.resolve("clientes.txt");

        Files.writeString(
                archivo,
                contenido.toString()
        );
    }
}
