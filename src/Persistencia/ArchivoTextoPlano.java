package Persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import LogicaBanco.Cliente;
import LogicaBanco.ProductoFinanciero;

public class ArchivoTextoPlano {

    public static void guardarClientesCSV(String nombreArchivo, List<Cliente> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Cliente cliente : clientes) {
                String linea = cliente.getId() + ";" + cliente.getNombre() + ";" + cliente.getApellido() + ";" +
                               cliente.getNumIdentificacion() + ";" + cliente.getNumTelefono();
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Clientes guardados en el archivo " + nombreArchivo + " exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public static List<Cliente> cargarClientesCSV(String nombreArchivo) {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 5) {
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    String apellido = partes[2];
                    int numIdentificacion = Integer.parseInt(partes[3]);
                    long numTelefono = Long.parseLong(partes[4]);
                    Cliente cliente = new Cliente(id, nombre, apellido, numIdentificacion, numTelefono);
                    clientes.add(cliente);
                }
            }
            System.out.println("Clientes cargados desde el archivo " + nombreArchivo + " exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
        return clientes;
    }
    public static void guardarProductosFinancierosClientesCSV(String nombreArchivo, List<Cliente> clientes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Cliente cliente : clientes) {
                if (!cliente.getProductosFinancieros().isEmpty()) {
                    for (ProductoFinanciero producto : cliente.getProductosFinancieros()) {
                        String linea = cliente.getId() + ";" + producto.getClass().getSimpleName() + ";" + producto.toString();
                        writer.write(linea);
                        writer.newLine();
                    }
                } else {
                    throw new IOException("El cliente " + cliente.getNombre() + " " + cliente.getApellido() + " no tiene productos financieros asociados.");
                }
            }
            System.out.println("Productos financieros de los clientes guardados en el archivo " + nombreArchivo + " exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de productos financieros de clientes: " + e.getMessage());
            throw e;
        }
    }
    
}
