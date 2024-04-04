package logica;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import Excepciones.*; 
import LogicaBanco.*; 
import TiposTransaccion.*; 
import ProductosFinancieros.*;

public class LogicaInterfaz {
    private Banco banco;
    private Scanner scanner;

    public LogicaInterfaz() {
        banco = new Banco();
        scanner = new Scanner(System.in);
    }


    void agregarCliente() {
        try {
            System.out.println("Ingrese los datos del cliente:");
            System.out.print("ID: ");
            String id = scanner.next();

            // Verificar si el ID ya existe
            for (Cliente clienteExistente : banco.getClientes()) {
                if (clienteExistente.getId().equals(id)) {
                    throw new BancoException("El ID ingresado ya está registrado para otro cliente.");
                }
            }

            System.out.print("Nombre: ");
            String nombre = scanner.next();
            System.out.print("Apellido: ");
            String apellido = scanner.next();
            System.out.print("Número de Identificación: ");
            long numIdentificacion = scanner.nextLong();
            System.out.print("Teléfono: ");
            String telefono = scanner.next();

            // Crear el cliente
            Cliente cliente = new Cliente(nombre, apellido, id, telefono, numIdentificacion);
            banco.agregarCliente(cliente);

            System.out.println("Cliente agregado correctamente. Su número de cuenta es: " + cliente.getNumeroCuenta());
        } catch (BancoException e) {
            System.out.println("Error al agregar el cliente: " + e.getMessage());
        }
    }

    void mostrarInfoClientes() {
        Collection<Cliente> clientes = banco.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados en el banco.");
        } else {
            System.out.println("Información de los clientes del banco:");
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Número de Identificación: " + cliente.getNumeroIdentificacion());
                System.out.println("Teléfono: " + cliente.getTelefono());
                System.out.println("Número de cuenta: " + cliente.getNumeroCuenta());
                System.out.println("Productos Financieros: ");
                for (ProductoFinanciero producto : cliente.getProductos()) {
                    System.out.println(producto.toString()); // Llama al método sobrescrito
                }
            }
        }
    }

    void agregarProductoFinancieroACliente() {
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

            validarTipoProducto(cliente, tipoProducto);

            // Lógica para crear y agregar el producto financiero al cliente
            switch (tipoProducto) {
                case 1:
                    CreditoHipotecario creditoHipotecario = crearCreditoHipotecario(cliente.getNumeroCuenta());
                    cliente.agregarProducto(creditoHipotecario);
                    System.out.println("Crédito hipotecario agregado correctamente al cliente con ID " + idCliente);
                    break;
                case 2:
                    CreditoLibreInversion creditoLibreInversion = crearCreditoLibreInversion(cliente.getNumeroCuenta());
                    cliente.agregarProducto(creditoLibreInversion);
                    System.out.println("Crédito de libre inversión agregado correctamente al cliente con ID " + idCliente);
                    break;
                case 3:
                    CuentaAhorros cuentaAhorros = crearCuentaAhorros(cliente.getNumeroCuenta());
                    cliente.agregarProducto(cuentaAhorros);
                    System.out.println("Cuenta de ahorros agregada correctamente al cliente con ID " + idCliente);
                    break;
                case 4:
                    CuentaCorriente cuentaCorriente = crearCuentaCorriente(cliente.getNumeroCuenta());
                    cliente.agregarProducto(cuentaCorriente);
                    System.out.println("Cuenta corriente agregada correctamente al cliente con ID " + idCliente);
                    break;
                case 5:
                    Seguro seguro = crearSeguro(cliente.getNumeroCuenta());
                    cliente.agregarProducto(seguro);
                    System.out.println("Seguro agregado correctamente al cliente con ID " + idCliente);
                    break;
                case 6:
                    TarjetaCredito tarjetaCredito = crearTarjetaCredito(cliente.getNumeroCuenta());
                    cliente.agregarProducto(tarjetaCredito);
                    System.out.println("Tarjeta de crédito agregada correctamente al cliente con ID " + idCliente);
                    break;
                case 7:
                    TarjetaDebito tarjetaDebito = crearTarjetaDebito(cliente.getNumeroCuenta());
                    cliente.agregarProducto(tarjetaDebito);
                    System.out.println("Tarjeta de débito agregada correctamente al cliente con ID " + idCliente);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } catch (BancoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void validarTipoProducto(Cliente cliente, int tipoProducto) throws BancoException {
        Class<?> tipoProductoClase = getTipoProductoPorNumero(tipoProducto);
        if (cliente.tieneTipoProducto(tipoProductoClase)) {
            throw new BancoException("El cliente ya tiene un producto financiero de este tipo.");
        }
    }

    private Class<?> getTipoProductoPorNumero(int numero) {
        switch (numero) {
            case 1:
                return CreditoHipotecario.class;
            case 2:
                return CreditoLibreInversion.class;
            case 3:
                return CuentaAhorros.class;
            case 4:
                return CuentaCorriente.class;
            case 5:
                return Seguro.class;
            case 6:
                return TarjetaCredito.class;
            case 7:
                return TarjetaDebito.class;
            default:
                return null;
        }
    }

    private CreditoHipotecario crearCreditoHipotecario(int numeroCuenta) {
        System.out.print("Ingrese el monto del préstamo: ");
        double montoPrestamo = scanner.nextDouble();
        System.out.print("Ingrese la tasa de interés: ");
        double tasaInteres = scanner.nextDouble();
        return new CreditoHipotecario(numeroCuenta, 0, montoPrestamo, tasaInteres);
    }

    private CreditoLibreInversion crearCreditoLibreInversion(int numeroCuenta) {
        System.out.print("Ingrese el monto del préstamo: ");
        double montoPrestamoLibreInversion = scanner.nextDouble();
        System.out.print("Ingrese la tasa de interés: ");
        double tasaInteresLibreInversion = scanner.nextDouble();
        return new CreditoLibreInversion(numeroCuenta, 0, montoPrestamoLibreInversion, tasaInteresLibreInversion);
    }

    private CuentaAhorros crearCuentaAhorros(int numeroCuenta) {
        System.out.print("Ingrese el saldo inicial de la cuenta de ahorros: ");
        double saldoInicialCA = scanner.nextDouble();
        System.out.print("Ingrese la tasa de interés de la cuenta de ahorros: ");
        double tasaInteresCA = scanner.nextDouble();
        return new CuentaAhorros(numeroCuenta, saldoInicialCA, tasaInteresCA);
    }

    private CuentaCorriente crearCuentaCorriente(int numeroCuenta) {
        System.out.print("Ingrese el saldo inicial de la cuenta corriente: ");
        double saldoInicialCC = scanner.nextDouble();
        System.out.print("Ingrese el sobregiro permitido: ");
        double sobregiroPermitido = scanner.nextDouble();
        return new CuentaCorriente(numeroCuenta, saldoInicialCC, sobregiroPermitido);
    }

    private Seguro crearSeguro(int numeroCuenta) {
        System.out.print("Ingrese el tipo de seguro: ");
        String tipoSeguro = scanner.next();
        System.out.print("Ingrese la prima del seguro: ");
        double primaSeguro = scanner.nextDouble();
        return new Seguro(numeroCuenta, 0, tipoSeguro, primaSeguro);
    }

    private TarjetaCredito crearTarjetaCredito(int numeroCuenta) {
        System.out.print("Ingrese el límite de crédito: ");
        double limiteCredito = scanner.nextDouble();
        System.out.print("Ingrese la tasa de interés de la tarjeta de crédito: ");
        double tasaInteresTC = scanner.nextDouble();
        return new TarjetaCredito(numeroCuenta, 0, limiteCredito, tasaInteresTC);
    }

    private TarjetaDebito crearTarjetaDebito(int numeroCuenta) {
        System.out.print("Ingrese el saldo inicial para la tarjeta de débito: ");
        double saldoInicialTD = scanner.nextDouble();
        return new TarjetaDebito(numeroCuenta, saldoInicialTD);
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
    void realizarTransaccion() {
        try {
            mostrarMenuTransacciones();
            int tipoTransaccion = obtenerOpcion();
            switch (tipoTransaccion) {
                case 1:
                    realizarConsignacion();
                    break;
                case 2:
                    pagarCredito();
                    break;
                default:
                    System.out.println("Tipo de transacción no válida.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido para el tipo de transacción.");
        }
    }

    private void mostrarMenuTransacciones() {
        System.out.println("Seleccione el tipo de transacción:");
        System.out.println("1. Consignar");
        System.out.println("2. Pagar Crédito");
    }

    private void realizarConsignacion() {
        try {
            mostrarMenuConsignacion();
            int tipoConsignacion = obtenerOpcion();
            switch (tipoConsignacion) {
                case 1:
                    consignarACuentaAhorros();
                    break;
                case 2:
                    consignarACuentaCorriente();
                    break;
                case 3:
                    consignarATarjetaDebito();
                    break;
                default:
                    System.out.println("Tipo de consignación no válida.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido para el tipo de consignación.");
        }
    }

    private void mostrarMenuConsignacion() {
        System.out.println("Seleccione el tipo de consignación:");
        System.out.println("1. Consignar a cuenta de ahorros");
        System.out.println("2. Consignar a cuenta corriente");
        System.out.println("3. Consignar a tarjeta de débito");
    }

    private void consignarACuentaAhorros() {
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
    }

    private void consignarACuentaCorriente() {
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
    }

    private void consignarATarjetaDebito() {
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
    }


    private void pagarCredito() {
        try {
            System.out.println("Ingrese el ID del cliente que desea realizar el pago:");
            String idCliente = scanner.next();

            Cliente cliente = banco.buscarCliente(idCliente);
            if (cliente == null) {
                throw new BancoException("Cliente no encontrado.");
            }

            System.out.println("Seleccione el tipo de crédito que desea pagar:");
            System.out.println("1. Crédito Hipotecario");
            System.out.println("2. Crédito de Libre Inversión");

            int tipoCredito = obtenerOpcion();
            switch (tipoCredito) {
                case 1:
                    pagarCreditoHipotecario(cliente);
                    break;
                case 2:
                    pagarCreditoLibreInversion(cliente);
                    break;
                default:
                    System.out.println("Tipo de crédito no válido.");
                    break;
            }
        } catch (BancoException e) {
            System.out.println("Error al realizar el pago del crédito: " + e.getMessage());
        }
    }


    private double leerMontoPago(String tipoCuenta, double saldoDisponible) {
        double montoPago;
        try (Scanner scanner = new Scanner(System.in)) {
			do {
			    try {
			        System.out.println("Ingrese el monto a pagar desde " + tipoCuenta + ":");
			        montoPago = scanner.nextDouble();
			        if (montoPago <= 0) {
			            System.out.println("El monto a pagar debe ser mayor que cero.");
			        } else if (montoPago > saldoDisponible) {
			            System.out.println("Fondos insuficientes en la cuenta seleccionada.");
			        } else {
			            return montoPago;
			        }
			    } catch (InputMismatchException e) {
			        System.out.println("Error: Debe ingresar un número válido.");
			        scanner.nextLine(); // Limpiar el buffer del scanner
			    }
			} while (true);
		}
    }

    private void pagarCreditoHipotecario(Cliente cliente) {
        // Obtener el crédito hipotecario del cliente y verificar su existencia
        CreditoHipotecario creditoHipotecario = (CreditoHipotecario) cliente.obtenerProductoPorTipo(CreditoHipotecario.class);
        if (creditoHipotecario == null) {
            System.out.println("El cliente no tiene un crédito hipotecario.");
            return;
        }

        // Obtener cuenta de ahorros, cuenta corriente y tarjeta de débito del cliente
        CuentaAhorros cuentaAhorros = (CuentaAhorros) cliente.obtenerProductoPorTipo(CuentaAhorros.class);
        CuentaCorriente cuentaCorriente = (CuentaCorriente) cliente.obtenerProductoPorTipo(CuentaCorriente.class);
        TarjetaDebito tarjetaDebito = (TarjetaDebito) cliente.obtenerProductoPorTipo(TarjetaDebito.class);

        // Verificar que el cliente tenga al menos uno de los productos para realizar el pago
        if (cuentaAhorros == null && cuentaCorriente == null && tarjetaDebito == null) {
            System.out.println("El cliente no tiene una cuenta de ahorros, cuenta corriente o tarjeta de débito para realizar el pago.");
            return;
        }

        // Mostrar opciones de pago al cliente
        System.out.println("Seleccione el producto para realizar el pago:");
        if (cuentaAhorros != null) {
            System.out.println("1. Cuenta de ahorros");
        }
        if (cuentaCorriente != null) {
            System.out.println("2. Cuenta corriente");
        }
        if (tarjetaDebito != null) {
            System.out.println("3. Tarjeta de débito");
        }

        // Obtener la opción de pago del cliente
        int opcionPago = obtenerOpcion();

        // Seleccionar el producto financiero para realizar el pago
        double montoPagoHipotecario;
        switch (opcionPago) {
            case 1:
                montoPagoHipotecario = leerMontoPago("Cuenta de ahorros", cuentaAhorros.getSaldo());
                // Retirar el monto del saldo de la cuenta de ahorros
                cuentaAhorros.retirar(montoPagoHipotecario);
                break;
            case 2:
                montoPagoHipotecario = leerMontoPago("Cuenta corriente", cuentaCorriente.getSaldo());
                // Retirar el monto del saldo de la cuenta corriente
                cuentaCorriente.retirar(montoPagoHipotecario);
                break;
            case 3:
                montoPagoHipotecario = leerMontoPago("Tarjeta de débito", tarjetaDebito.getSaldo());
                // Retirar el monto del saldo de la tarjeta de débito
                tarjetaDebito.retirar(montoPagoHipotecario);
                break;
            default:
                System.out.println("Opción de pago no válida.");
                return;
        }

        try {
            // Realizar el pago del crédito hipotecario
            PagoCreditoHipotecario pagoHipotecario = new PagoCreditoHipotecario(montoPagoHipotecario);
            pagoHipotecario.ejecutar(creditoHipotecario);

            // Mostrar la deuda restante después del pago
            System.out.println("Deuda restante del crédito hipotecario: $" + creditoHipotecario.getDeuda());

            // Eliminar el crédito hipotecario del cliente si la deuda es cero
            if (creditoHipotecario.getDeuda() == 0) {
                cliente.eliminarProducto(creditoHipotecario);
                System.out.println("El crédito hipotecario ha sido saldado completamente y eliminado del cliente.");
            }

            System.out.println("Pago realizado exitosamente.");
        } catch (BancoException e) {
            System.out.println("Error al realizar el pago: " + e.getMessage());
        }
    }

    private void pagarCreditoLibreInversion(Cliente cliente) {
        // Obtener el crédito de libre inversión del cliente y verificar su existencia
        CreditoLibreInversion creditoLibreInversion = (CreditoLibreInversion) cliente.obtenerProductoPorTipo(CreditoLibreInversion.class);
        if (creditoLibreInversion == null) {
            System.out.println("El cliente no tiene un crédito de libre inversión.");
            return;
        }

        // Obtener cuenta de ahorros, cuenta corriente y tarjeta de débito del cliente
        CuentaAhorros cuentaAhorros = (CuentaAhorros) cliente.obtenerProductoPorTipo(CuentaAhorros.class);
        CuentaCorriente cuentaCorriente = (CuentaCorriente) cliente.obtenerProductoPorTipo(CuentaCorriente.class);
        TarjetaDebito tarjetaDebito = (TarjetaDebito) cliente.obtenerProductoPorTipo(TarjetaDebito.class);

        // Verificar que el cliente tenga al menos uno de los productos para realizar el pago
        if (cuentaAhorros == null && cuentaCorriente == null && tarjetaDebito == null) {
            System.out.println("El cliente no tiene una cuenta de ahorros, cuenta corriente o tarjeta de débito para realizar el pago.");
            return;
        }

        // Mostrar opciones de pago al cliente
        System.out.println("Seleccione el producto para realizar el pago:");
        if (cuentaAhorros != null) {
            System.out.println("1. Cuenta de ahorros");
        }
        if (cuentaCorriente != null) {
            System.out.println("2. Cuenta corriente");
        }
        if (tarjetaDebito != null) {
            System.out.println("3. Tarjeta de débito");
        }

        // Obtener la opción de pago del cliente
        int opcionPago = obtenerOpcion();

        // Seleccionar el producto financiero para realizar el pago
        double montoPagoLibreInversion;
        switch (opcionPago) {
            case 1:
                montoPagoLibreInversion = leerMontoPago("Cuenta de ahorros", cuentaAhorros.getSaldo());
                // Retirar el monto del saldo de la cuenta de ahorros
                cuentaAhorros.retirar(montoPagoLibreInversion);
                break;
            case 2:
                montoPagoLibreInversion = leerMontoPago("Cuenta corriente", cuentaCorriente.getSaldo());
                // Retirar el monto del saldo de la cuenta corriente
                cuentaCorriente.retirar(montoPagoLibreInversion);
                break;
            case 3:
                montoPagoLibreInversion = leerMontoPago("Tarjeta de débito", tarjetaDebito.getSaldo());
                // Retirar el monto del saldo de la tarjeta de débito
                tarjetaDebito.retirar(montoPagoLibreInversion);
                break;
            default:
                System.out.println("Opción de pago no válida.");
                return;
        }

        try {
            // Verificar si el monto a pagar excede la deuda del crédito de libre inversión
            if (montoPagoLibreInversion > creditoLibreInversion.getDeuda()) {
                throw new BancoException("El monto a pagar excede la deuda del crédito de libre inversión.");
            }

            // Realizar el pago del crédito de libre inversión
            PagoCreditoLibreInversion pagoLibreInversion = new PagoCreditoLibreInversion(montoPagoLibreInversion);
            pagoLibreInversion.ejecutar(creditoLibreInversion);

            // Mostrar la deuda restante después del pago
            System.out.println("Deuda restante del crédito de libre inversión: $" + creditoLibreInversion.getDeuda());

            // Eliminar el crédito de libre inversión del cliente si la deuda es cero
            if (creditoLibreInversion.getDeuda() == 0) {
                cliente.eliminarProducto(creditoLibreInversion);
                System.out.println("El crédito de libre inversión ha sido saldado completamente y eliminado del cliente.");
            }

            System.out.println("Pago realizado exitosamente.");
        } catch (BancoException e) {
            System.out.println("Error al realizar el pago: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        Interfaz menu = new Interfaz();
        menu.mostrarMenu();
    }
}