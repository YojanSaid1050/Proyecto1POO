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
            System.out.println("3. Agregar producto financiero a cliente");
            System.out.println("4. Salir");
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
                    agregarProductoFinancieroACliente();
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese un número válido.");
            }
        } while (opcion != 4);
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
            long numIdentificacion = scanner.nextLong();

            for (Cliente cliente : banco.getClientes()) {
                if (cliente.getNumeroIdentificacion() == numIdentificacion && !cliente.getId().equals(id)) {
                    throw new BancoException("El número de identificación ingresado ya está registrado con otra ID.");
                }
            }

            System.out.print("Teléfono: ");
            String telefono = scanner.next();

            Cliente cliente = new Cliente(nombre, apellido, id, telefono, numIdentificacion);
            banco.agregarCliente(cliente);
            System.out.println("Cliente agregado correctamente. Su número de cuenta es: " + cliente.getNextNumeroCuenta());
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

    private void agregarProductoFinancieroACliente() {
        try {
            System.out.println("Ingrese el ID del cliente al que desea agregar el producto financiero:");
            String idCliente = scanner.next();

            Cliente cliente = banco.buscarCliente(idCliente);
            if (cliente == null) {
                throw new BancoException("El cliente con ID " + idCliente + " no existe en el banco.");
            }

            // Verificar si el cliente ya tiene un producto financiero del mismo tipo
            System.out.println("Seleccione el tipo de producto financiero:");
            System.out.println("1. Credito Hipotecario");
            // Agrega más opciones según los tipos de productos financieros disponibles

            int tipoProducto = scanner.nextInt();
            if (cliente.tieneTipoProducto(CreditoHipotecario.class)) {
                throw new BancoException("El cliente ya tiene un producto financiero del mismo tipo.");
            }

            // Crear el producto financiero y agregarlo al cliente
            int numeroCuenta = cliente.getNextNumeroCuenta();
            switch (tipoProducto) {
                case 1:
                    // Otros campos necesarios para crear el producto financiero
                    System.out.print("Ingrese el monto del préstamo: ");
                    double montoPrestamo = scanner.nextDouble();
                    System.out.print("Ingrese la tasa de interés: ");
                    double tasaInteres = scanner.nextDouble();

                    // Crear el crédito hipotecario y agregarlo al cliente
                    CreditoHipotecario creditoHipotecario = new CreditoHipotecario(numeroCuenta, 0, montoPrestamo, tasaInteres);
                    cliente.agregarProducto(creditoHipotecario);
                    System.out.println("Credito hipotecario agregado correctamente al cliente con ID " + idCliente);
                    break;
                // Agrega más casos para otros tipos de productos financieros
                default:
                    System.out.println("Opción no válida.");
            }
        } catch (BancoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}
