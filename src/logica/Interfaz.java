package logica;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Interfaz {
    private LogicaInterfaz LogicaInterfaz;
    private Scanner scanner;

    public Interfaz() {
        LogicaInterfaz = new LogicaInterfaz();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = obtenerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 5);
    }

    private void mostrarOpciones() {
        System.out.println("***** Menú *****");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Mostrar información de clientes");
        System.out.println("3. Agregar producto financiero a cliente");
        System.out.println("4. Realizar transacción");
        System.out.println("5. Salir");
        System.out.print("Ingrese su opción: ");
    }

    private int obtenerOpcion() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine(); // Limpiar el buffer del scanner
            return 0;
        }
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                LogicaInterfaz.agregarCliente();
                break;
            case 2:
            	LogicaInterfaz.mostrarInfoClientes();
                break;
            case 3:
            	LogicaInterfaz.agregarProductoFinancieroACliente();
                break;
            case 4:
            	LogicaInterfaz.realizarTransaccion();
                break;
            case 5:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción inválida. Por favor, ingrese un número válido.");
        }
    }

    public static void main(String[] args) {
        Interfaz menu = new Interfaz();
        menu.mostrarMenu();
    }
}