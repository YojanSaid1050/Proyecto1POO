import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoCSV {
    
  
    public static void guardarArchivosCSV(ArrayList<String> datos, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String linea : datos) {
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Datos guardados en el archivo CSV correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar datos en el archivo CSV: " + e.getMessage());
        }
    }
    
    public static ArrayList<String> cargarArchivosCSV(String nombreArchivo) {
        ArrayList<String> datos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                datos.add(linea);
            }
            System.out.println("Datos cargados desde el archivo CSV correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar datos desde el archivo CSV: " + e.getMessage());
        }
        return datos;
    }
}