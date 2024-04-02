import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
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
            System.out.println("4. Realizar transacción");
            System.out.println("5. Salir");
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
                    realizarTransaccion(); // Llamada al método realizarTransaccion()
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese un número válido.");
            }
        } while (opcion != 5);
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
                    try {
                        // Obtener el ID del cliente
                        System.out.print("Ingrese el ID del cliente: ");
                        String idClienteCA = scanner.next();

                        // Verificar si el cliente existe
                        Cliente clienteCA = banco.buscarCliente(idClienteCA);
                        if (clienteCA == null) {
                            throw new BancoException("Cliente no encontrado.");
                        }

                        // Agregar la cuenta de ahorros al cliente
                        int numeroCuentaCA = clienteCA.getNumeroCuenta(); // Obtener el número de cuenta del cliente
                        System.out.print("Ingrese el saldo inicial de la cuenta de ahorros: ");
                        double saldoInicialCA = scanner.nextDouble();
                        System.out.print("Ingrese la tasa de interés de la cuenta de ahorros: ");
                        double tasaInteresCA = scanner.nextDouble();

                        // Crear la cuenta de ahorros y asociarla al cliente
                        CuentaAhorros cuentaAhorros = new CuentaAhorros(numeroCuentaCA, saldoInicialCA, tasaInteresCA);
                        clienteCA.agregarProducto(cuentaAhorros);
                        System.out.println("Cuenta de ahorros agregada correctamente al cliente.");
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Ingrese un valor válido.");
                        scanner.next(); // Limpiar el buffer del scanner
                    } catch (BancoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
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
                        // Obtener el ID del cliente
                        System.out.print("Ingrese el ID del cliente: ");
                        String idClienteTD = scanner.next();

                        // Verificar si el cliente existe
                        Cliente clienteTD = banco.buscarCliente(idClienteTD);
                        if (clienteTD == null) {
                            throw new BancoException("Cliente no encontrado.");
                        }

                        // Verificar si el cliente ya tiene una tarjeta de débito
                        if (clienteTD.tieneTipoProducto(TarjetaDebito.class)) {
                            throw new BancoException("El cliente ya tiene una tarjeta de débito.");
                        }

                        // Obtener el saldo inicial para la tarjeta de débito
                        System.out.print("Ingrese el saldo inicial para la tarjeta de débito: ");
                        double saldoInicialTD = scanner.nextDouble();

                        // Crear la tarjeta de débito con los datos proporcionados
                        TarjetaDebito tarjetaDebito = new TarjetaDebito(clienteTD.getNumeroCuenta(), saldoInicialTD);
                        clienteTD.agregarProducto(tarjetaDebito);
                        System.out.println("Tarjeta de débito agregada correctamente al cliente con ID " + idClienteTD);
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Ingrese un valor válido.");
                        scanner.next(); // Limpiar el buffer del scanner
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


    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
    private void realizarTransaccion() {
        try {
            System.out.println("Seleccione el tipo de transacción:");
            System.out.println("1. Consignar");
            System.out.println("2. Pagar Crédito");

            int tipoTransaccion = scanner.nextInt();
            switch (tipoTransaccion) {
                case 1:
                    System.out.println("Seleccione el tipo de consignación:");
                    System.out.println("1. Consignar a cuenta de ahorros");
                    System.out.println("2. Consignar a cuenta corriente");
                    System.out.println("3. Consignar a tarjeta de débito");

                    int tipoConsignacion = scanner.nextInt();
                    switch (tipoConsignacion) {
                    case 1:
                        try {
                            System.out.println("Ingrese el ID del cliente al que desea consignar a la cuenta de ahorros:");
                            String idCliente = scanner.next();

                            Cliente cliente = banco.buscarCliente(idCliente);
                            if (cliente == null) {
                                throw new BancoException("Cliente no encontrado.");
                            }

                            // Verificar si el cliente tiene una cuenta de ahorros
                            CuentaAhorros cuentaAhorros = (CuentaAhorros) cliente.obtenerProductoPorTipo(CuentaAhorros.class);
                            if (cuentaAhorros == null) {
                                throw new BancoException("El cliente no tiene una cuenta de ahorros.");
                            }

                            System.out.println("Ingrese el monto a consignar:");
                            double montoConsignacion = scanner.nextDouble();
                            Consignacion consignacion = new Consignacion(montoConsignacion);
                            cuentaAhorros.procesarTransaccion(consignacion);
                            System.out.println("Consignación realizada con éxito en la cuenta de ahorros del cliente.");
                        } catch (BancoException e) {
                            System.out.println("Error al realizar la consignación: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            System.out.println("Ingrese el ID del cliente al que desea consignar a la cuenta corriente:");
                            String idClienteCC = scanner.next();

                            Cliente clienteCC = banco.buscarCliente(idClienteCC);
                            if (clienteCC == null) {
                                throw new BancoException("El cliente con ID " + idClienteCC + " no existe en el banco.");
                            }

                            // Verificar si el cliente tiene una cuenta corriente
                            CuentaCorriente cuentaCorriente = (CuentaCorriente) clienteCC.obtenerProductoPorTipo(CuentaCorriente.class);
                            if (cuentaCorriente == null) {
                                throw new BancoException("El cliente no tiene una cuenta corriente.");
                            }

                            System.out.println("Ingrese el monto a consignar:");
                            double montoConsignacionCC = scanner.nextDouble();
                            Consignacion consignacionCC = new Consignacion(montoConsignacionCC);
                            cuentaCorriente.procesarTransaccion(consignacionCC);
                            System.out.println("Consignación realizada con éxito en la cuenta corriente del cliente.");
                        } catch (BancoException e) {
                            System.out.println("Error al realizar la consignación: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            System.out.println("Ingrese el ID del cliente al que desea consignar a la tarjeta de débito:");
                            String idClienteTD = scanner.next();

                            Cliente clienteTD = banco.buscarCliente(idClienteTD);
                            if (clienteTD == null) {
                                throw new BancoException("El cliente con ID " + idClienteTD + " no existe en el banco.");
                            }

                            // Verificar si el cliente tiene una tarjeta de débito
                            TarjetaDebito tarjetaDebito = (TarjetaDebito) clienteTD.obtenerProductoPorTipo(TarjetaDebito.class);
                            if (tarjetaDebito == null) {
                                throw new BancoException("El cliente no tiene una tarjeta de débito.");
                            }

                            System.out.println("Ingrese el monto a consignar:");
                            double montoConsignacionTD = scanner.nextDouble();
                            Consignacion consignacionTD = new Consignacion(montoConsignacionTD);
                            tarjetaDebito.procesarTransaccion(consignacionTD);
                            System.out.println("Consignación realizada con éxito en la tarjeta de débito del cliente.");
                        } catch (BancoException e) {
                            System.out.println("Error al realizar la consignación: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Tipo de consignación no válido.");
                        break;
                    }
                    break;
                case 2:
                    System.out.println("Seleccione el tipo de crédito a pagar:");
                    System.out.println("1. Crédito Hipotecario");
                    System.out.println("2. Crédito de Libre Inversión");

                    int tipoCreditoPago = scanner.nextInt();
                    switch (tipoCreditoPago) {
                    case 1:
                        try {
                            System.out.println("Ingrese el ID del cliente que desea pagar el crédito hipotecario:");
                            String idClienteHipotecario = scanner.next();
                            Cliente clienteHipotecario = banco.buscarCliente(idClienteHipotecario);
                            if (clienteHipotecario == null) {
                                throw new BancoException("Cliente no encontrado.");
                            }

                            // Verificar si el cliente tiene un crédito hipotecario
                            if (!clienteHipotecario.tieneTipoProducto(CreditoHipotecario.class)) {
                                throw new BancoException("El cliente no tiene un crédito hipotecario.");
                            }

                            // Obtener el crédito hipotecario del cliente
                            CreditoHipotecario creditoHipotecario = null;
                            for (ProductoFinanciero producto : clienteHipotecario.getProductos()) {
                                if (producto instanceof CreditoHipotecario) {
                                    creditoHipotecario = (CreditoHipotecario) producto;
                                    break;
                                }
                            }
                            if (creditoHipotecario == null) {
                                throw new BancoException("Error al encontrar el crédito hipotecario del cliente.");
                            }

                            // Solicitar el número de cuenta del cliente
                            System.out.println("Ingrese el número de cuenta del cliente:");
                            int numeroCuentaCliente = scanner.nextInt();

                            // Verificar si el número de cuenta concuerda con el ID del cliente
                            if (numeroCuentaCliente != clienteHipotecario.getNumeroCuenta()) {
                                throw new BancoException("El número de cuenta no coincide con el cliente.");
                            }

                            // Verificar si el cliente tiene cuentas de ahorros, cuenta corriente o tarjeta de débito
                            List<Class<? extends ProductoFinanciero>> tiposCuentas = Arrays.asList(CuentaAhorros.class, CuentaCorriente.class, TarjetaDebito.class);
                            boolean tieneCuentas = false;
                            for (Class<? extends ProductoFinanciero> tipo : tiposCuentas) {
                                if (clienteHipotecario.tieneTipoProducto(tipo)) {
                                    tieneCuentas = true;
                                    break;
                                }
                            }
                            if (!tieneCuentas) {
                                throw new BancoException("El cliente no tiene cuenta de ahorros, cuenta corriente o tarjeta de débito para realizar el pago.");
                            }

                            // Solicitar el tipo de cuenta para pagar la deuda
                            System.out.println("Seleccione el tipo de cuenta para pagar la deuda:");
                            System.out.println("1. Cuenta de ahorros");
                            System.out.println("2. Cuenta corriente");
                            System.out.println("3. Tarjeta de débito");
                            int tipoCuentaPago = scanner.nextInt();

                            // Obtener el producto financiero correspondiente para realizar el pago
                            ProductoFinanciero cuentaPago = null;
                            switch (tipoCuentaPago) {
                                case 1:
                                    if (clienteHipotecario.tieneTipoProducto(CuentaAhorros.class)) {
                                        cuentaPago = clienteHipotecario.obtenerProductoPorTipo(CuentaAhorros.class);
                                    } else {
                                        throw new BancoException("El cliente no tiene una cuenta de ahorros para realizar el pago.");
                                    }
                                    break;
                                case 2:
                                    if (clienteHipotecario.tieneTipoProducto(CuentaCorriente.class)) {
                                        cuentaPago = clienteHipotecario.obtenerProductoPorTipo(CuentaCorriente.class);
                                    } else {
                                        throw new BancoException("El cliente no tiene una cuenta corriente para realizar el pago.");
                                    }
                                    break;
                                case 3:
                                    if (clienteHipotecario.tieneTipoProducto(TarjetaDebito.class)) {
                                        cuentaPago = clienteHipotecario.obtenerProductoPorTipo(TarjetaDebito.class);
                                    } else {
                                        throw new BancoException("El cliente no tiene una tarjeta de débito para realizar el pago.");
                                    }
                                    break;
                                default:
                                    throw new BancoException("Tipo de cuenta no válido.");
                            }

                            // Solicitar información adicional para confirmar el pago
                            System.out.println("Ingrese el número de cuenta asociado a la cuenta seleccionada:");
                            int numeroCuentaConfirmacion = scanner.nextInt();
                            System.out.println("Ingrese el ID asociado a la cuenta seleccionada:");
                            String idConfirmacion = scanner.next();
                            System.out.println("Ingrese el número de identificación asociado a la cuenta seleccionada:");
                            int numeroIdentificacionConfirmacion = scanner.nextInt();

                            // Verificar si la información ingresada coincide con los datos del cliente y la cuenta seleccionada
                            if (numeroCuentaConfirmacion != cuentaPago.getNumeroCuenta() || !idConfirmacion.equals(clienteHipotecario.getId()) || numeroIdentificacionConfirmacion != clienteHipotecario.getNumeroIdentificacion()) {
                                throw new BancoException("La información ingresada no coincide con los datos del cliente y la cuenta seleccionada.");
                            }

                            // Mostrar la deuda actual
                            System.out.println("Deuda actual del crédito hipotecario: $" + creditoHipotecario.getDeuda());

                            // Solicitar el monto a pagar
                            System.out.println("Ingrese el monto a pagar:");
                            double montoPago = scanner.nextDouble();

                            // Verificar si el saldo de la cuenta es suficiente para realizar el pago
                            if (cuentaPago.getSaldo() < montoPago) {
                                throw new BancoException("La cuenta seleccionada no tiene saldo suficiente para realizar el pago.");
                            }

                            // Procesar la transacción de pago del crédito hipotecario
                            Transaccion pagoCreditoHipotecario = new PagoCreditoHipotecario(montoPago);
                            cuentaPago.procesarTransaccion(pagoCreditoHipotecario);

                            // Actualizar la deuda restante del crédito hipotecario
                            System.out.println("Deuda restante del crédito hipotecario: $" + creditoHipotecario.getDeuda());

                            // Eliminar el producto financiero si la deuda se ha pagado completamente
                            if (creditoHipotecario.getDeuda() == 0) {
                                clienteHipotecario.eliminarProducto(creditoHipotecario);
                                System.out.println("¡La deuda del crédito hipotecario ha sido pagada completamente!");
                            }
                        } catch (BancoException e) {
                            System.out.println("Error al realizar el pago del crédito hipotecario: " + e.getMessage());
                        }
                        break;

                        case 2:
                            // Lógica para pagar crédito de libre inversión
                            break;
                        default:
                            System.out.println("Tipo de crédito no válido.");
                            break;
                    }
                    break;

                default:
                    System.out.println("Tipo de transacción no válida.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido para el tipo de transacción.");
            scanner.next(); // Limpiar el buffer del scanner
        }
    }
}