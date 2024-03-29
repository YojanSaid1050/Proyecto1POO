import java.util.Scanner;

public class Menu {

    private Banco banco;
    private Scanner scanner;

    public Menu() {
        banco = new Banco();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("***** Menú *****");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Mostrar información de clientes");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    mostrarInfoClientes();
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese un número válido.");
            }
        } while (opcion != 3);
    }

    private void agregarCliente() {
        try {
            System.out.println("Ingrese los datos del cliente:");
            System.out.print("ID: ");
            String id = scanner.next();

            if (banco.buscarCliente(id) != null) {
                throw new BancoException("La ID ingresada ya está registrada en el banco.");
            }

            System.out.print("Nombre: ");
            String nombre = scanner.next();
            System.out.print("Apellido: ");
            String apellido = scanner.next();
            System.out.print("Número de Identificación: ");
            int numIdentificacion = scanner.nextInt();

            // Verificar si el número de identificación ya está registrado con otro ID
            for (Cliente cliente : banco.getClientes()) {
                if (cliente.getNumeroIdentificacion() == numIdentificacion && !cliente.getId().equals(id)) {
                    throw new BancoException("El número de identificación ingresado ya está registrado con otra ID.");
                }
            }

            System.out.print("Teléfono: ");
            String telefono = scanner.next();

            Cliente cliente = new Cliente(id, nombre, apellido, numIdentificacion, telefono);
            banco.agregarCliente(cliente);
        } catch (BancoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void mostrarInfoClientes() {
        System.out.println("***** Información de Clientes *****");
        for (Cliente cliente : banco.getClientes()) {
            System.out.println(cliente);
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}
