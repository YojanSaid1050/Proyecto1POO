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

            // Agregar el cliente con su respectivo número de cuenta
            Cliente cliente = new Cliente(nombre, apellido, id, telefono, numIdentificacion);
            cliente.setNumeroCuenta(banco.getNextNumeroCuenta());
            banco.agregarCliente(cliente);

            System.out.println("Cliente agregado correctamente. Su número de cuenta es: " + cliente.getNumeroCuenta());
        } catch (BancoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarInfoClientes() {
        System.out.println("***** Información de Clientes *****");
        for (Cliente cliente : banco.getClientes()) {
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido: " + cliente.getApellido());
            System.out.println("Número de Identificación: " + cliente.getNumeroIdentificacion());
            System.out.println("Número de Cuenta: " + cliente.getNumeroCuenta());
            System.out.println("Productos Financieros:");

            boolean tieneProductosFinancieros = false;

            // Recorrer los productos financieros del cliente
            for (ProductoFinanciero producto : cliente.getProductos()) {
                System.out.println("\t- " + producto);
                tieneProductosFinancieros = true;
            }

            // Verificar si el cliente no tiene ningún producto financiero
            if (!tieneProductosFinancieros) {
                System.out.println("\t- Sin Productos Financieros");
            }

            System.out.println();
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

            System.out.println("Seleccione el tipo de producto financiero:");
            System.out.println("1. Crédito Hipotecario");
            System.out.println("2. Crédito de Libre Inversión");
            System.out.println("3. Cuenta de Ahorros");
            System.out.println("4. Cuenta Corriente");
            System.out.println("5. Seguro");
            System.out.println("6. Tarjeta de Crédito");
            System.out.println("7. Tarjeta de Débito");

            int tipoProducto = scanner.nextInt();
            if (tipoProducto < 1 || tipoProducto > 7) {
                throw new BancoException("Opción no válida.");
            }

            // Verificar si el cliente ya tiene un producto financiero del mismo tipo
            if (tipoProducto == 1 && cliente.tieneTipoProducto(CreditoHipotecario.class)) {
                throw new BancoException("El cliente ya tiene un crédito hipotecario.");
            } else if (tipoProducto == 2 && cliente.tieneTipoProducto(CreditoLibreInversion.class)) {
                throw new BancoException("El cliente ya tiene un crédito de libre inversión.");
            } else if (tipoProducto == 3 && cliente.tieneTipoProducto(CuentaAhorros.class)) {
                throw new BancoException("El cliente ya tiene una cuenta de ahorros.");
            } else if (tipoProducto == 4 && cliente.tieneTipoProducto(CuentaCorriente.class)) {
                throw new BancoException("El cliente ya tiene una cuenta corriente.");
            } else if (tipoProducto == 5 && cliente.tieneTipoProducto(Seguro.class)) {
                throw new BancoException("El cliente ya tiene un seguro.");
            } else if (tipoProducto == 6 && cliente.tieneTipoProducto(TarjetaCredito.class)) {
                throw new BancoException("El cliente ya tiene una tarjeta de crédito.");
            } else if (tipoProducto == 7 && cliente.tieneTipoProducto(TarjetaDebito.class)) {
                throw new BancoException("El cliente ya tiene una tarjeta de débito.");
            }

            // Crear el producto financiero y agregarlo al cliente
            int numeroCuenta = cliente.getNumeroCuenta(); // Obtener el número de cuenta del cliente
            switch (tipoProducto) {
                case 1:
                    // Crear un crédito hipotecario
                    System.out.print("Ingrese el monto del préstamo: ");
                    double montoPrestamo = scanner.nextDouble();
                    System.out.print("Ingrese la tasa de interés: ");
                    double tasaInteres = scanner.nextDouble();
                    CreditoHipotecario creditoHipotecario = new CreditoHipotecario(numeroCuenta, 0, montoPrestamo, tasaInteres);
                    cliente.agregarProducto(creditoHipotecario);
                    System.out.println("Crédito hipotecario agregado correctamente al cliente con ID " + idCliente);
                    break;
                case 2:
                    // Crear un crédito de libre inversión
                    System.out.print("Ingrese el monto del préstamo: ");
                    double montoPrestamoLibreInversion = scanner.nextDouble();
                    System.out.print("Ingrese la tasa de interés: ");
                    double tasaInteresLibreInversion = scanner.nextDouble();
                    CreditoLibreInversion creditoLibreInversion = new CreditoLibreInversion(numeroCuenta, 0, montoPrestamoLibreInversion, tasaInteresLibreInversion);
                    cliente.agregarProducto(creditoLibreInversion);
                    System.out.println("Crédito de libre inversión agregado correctamente al cliente con ID " + idCliente);
                    break;
                case 3:
                    // Crear una cuenta de ahorros
                    agregarCuentaAhorrosACliente(cliente);
                    break;
                case 4:
                    // Agregar una cuenta corriente
                    try {
                        System.out.println("Ingrese el ID del cliente al que desea agregar la cuenta corriente:");
                        String idClienteCC = scanner.next();

                        Cliente clienteCC = banco.buscarCliente(idClienteCC);
                        if (clienteCC == null) {
                            throw new BancoException("El cliente con ID " + idClienteCC + " no existe en el banco.");
                        }

                        // Verificar si el cliente ya tiene una cuenta corriente
                        if (clienteCC.tieneTipoProducto(CuentaCorriente.class)) {
                            throw new BancoException("El cliente ya tiene una cuenta corriente.");
                        }

                        // Crear la cuenta corriente y agregarla al cliente
                        int numeroCuentaCC = clienteCC.getNumeroCuenta(); // Obtener el número de cuenta del cliente
                        System.out.print("Ingrese el saldo inicial de la cuenta corriente: ");
                        double saldoInicialCC = scanner.nextDouble();
                        System.out.print("Ingrese el sobregiro permitido: ");
                        double sobregiroPermitido = scanner.nextDouble();
                        CuentaCorriente cuentaCorriente = new CuentaCorriente(numeroCuentaCC, saldoInicialCC, sobregiroPermitido);
                        clienteCC.agregarProducto(cuentaCorriente);
                        System.out.println("Cuenta corriente agregada correctamente al cliente con ID " + idClienteCC);
                    } catch (BancoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    // Crear seguro
                    System.out.print("Ingrese el tipo de seguro: ");
                    String tipoSeguro = scanner.next();
                    System.out.print("Ingrese la prima del seguro: ");
                    double primaSeguro = scanner.nextDouble();
                    Seguro seguro = new Seguro(numeroCuenta, 0, tipoSeguro, primaSeguro);
                    cliente.agregarProducto(seguro);
                    System.out.println("Seguro agregado correctamente al cliente con ID " + idCliente);
                    break;
                case 6:
                    // Agregar una tarjeta de crédito
                    try {
                        System.out.println("Ingrese el ID del cliente al que desea agregar la tarjeta de crédito:");
                        String idClienteTC = scanner.next();

                        Cliente clienteTC = banco.buscarCliente(idClienteTC);
                        if (clienteTC == null) {
                            throw new BancoException("El cliente con ID " + idClienteTC + " no existe en el banco.");
                        }

                        // Verificar si el cliente ya tiene una tarjeta de crédito
                        if (clienteTC.tieneTipoProducto(TarjetaCredito.class)) {
                            throw new BancoException("El cliente ya tiene una tarjeta de crédito.");
                        }

                        // Crear la tarjeta de crédito y agregarla al cliente
                        int numeroCuentaTC = clienteTC.getNumeroCuenta(); // Obtener el número de cuenta del cliente
                        System.out.print("Ingrese el límite de crédito: ");
                        double limiteCredito = scanner.nextDouble();
                        System.out.print("Ingrese la tasa de interés de la tarjeta de crédito: ");
                        double tasaInteresTC = scanner.nextDouble(); // Cambiar el nombre de la variable
                        TarjetaCredito tarjetaCredito = new TarjetaCredito(numeroCuentaTC, 0, limiteCredito, tasaInteresTC); // Usar el nuevo nombre de la variable
                        clienteTC.agregarProducto(tarjetaCredito);
                        System.out.println("Tarjeta de crédito agregada correctamente al cliente con ID " + idClienteTC);
                    } catch (BancoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        // Verificar si el cliente ya tiene una tarjeta de débito
                        if (cliente.tieneTipoProducto(TarjetaDebito.class)) {
                            throw new BancoException("El cliente ya tiene una tarjeta de débito.");
                        }

                        // Obtener la cuenta de ahorros del cliente si existe
                        CuentaAhorros cuentaAhorrosExistente = null;
                        for (ProductoFinanciero producto : cliente.getProductos()) {
                            if (producto instanceof CuentaAhorros) {
                                cuentaAhorrosExistente = (CuentaAhorros) producto;
                                break;
                            }
                        }

                        // Si no tiene cuenta de ahorros, lanzar una excepción o crear una cuenta de ahorros
                        if (cuentaAhorrosExistente == null) {
                            System.out.println("Creando una nueva cuenta de ahorros...");
                            agregarCuentaAhorrosACliente(cliente);
                            cuentaAhorrosExistente = (CuentaAhorros) cliente.getProductos().get(cliente.getProductos().size() - 1);
                        }

                        // Obtener el saldo de la cuenta de ahorros
                        double saldoInicialTD = cuentaAhorrosExistente.getSaldo();

                        // Obtener el número de cuenta de la cuenta de ahorros
                        int numeroCuentaTD = cuentaAhorrosExistente.getNumeroCuenta();

                        // Obtener la tasa de interés de la cuenta de ahorros
                        double tasaInteresTD = cuentaAhorrosExistente.getTasaInteres();

                        // Crear la tarjeta de débito con el saldo de la cuenta de ahorros como saldo inicial
                        TarjetaDebito tarjetaDebito = new TarjetaDebito(numeroCuentaTD, saldoInicialTD, tasaInteresTD);
                        cliente.agregarProducto(tarjetaDebito);
                        System.out.println("Tarjeta de débito agregada correctamente al cliente con ID " + idCliente);
                    } catch (BancoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;



                default:
                    System.out.println("Opción no válida.");
            }
        } catch (BancoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCuentaAhorrosACliente(Cliente cliente) {
        int numeroCuenta = cliente.getNumeroCuenta(); // Obtener el número de cuenta del cliente
        System.out.print("Ingrese el saldo inicial de la cuenta de ahorros: ");
        double saldoInicial = scanner.nextDouble();
        System.out.print("Ingrese la tasa de interés de la cuenta de ahorros: ");
        double tasaInteres = scanner.nextDouble();

        // Crear la cuenta de ahorros y asociarla al cliente
        CuentaAhorros cuentaAhorros = new CuentaAhorros(numeroCuenta, saldoInicial, false, tasaInteres);
        cliente.agregarProducto(cuentaAhorros);
        System.out.println("Cuenta de ahorros agregada correctamente al cliente.");
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}
