package ProductosFinancieros;

import LogicaBanco.*;
import java.util.Scanner;
import Excepciones.BancoException;

public class AgregarProductoFinanciero {
    private Banco banco;

    public AgregarProductoFinanciero(Banco banco) {
        this.banco = banco;
    }

    public void agregarProductoFinanciero(Scanner scanner) {
        try {
            System.out.println("Agregar producto financiero a un cliente");
            System.out.print("Ingrese el ID del cliente: ");
            int idCliente = scanner.nextInt();

            Cliente cliente = buscarClientePorID(idCliente);
            if (cliente == null) {
                throw new BancoException("No existe un cliente con ese ID");
            }

            scanner.nextLine();

            System.out.println("Seleccione el tipo de producto financiero:");
            System.out.println("1. Cuenta de ahorros");
            System.out.println("2. Cuenta corriente");
            System.out.println("3. Crédito hipotecario");
            System.out.println("4. Crédito de libre inversión");
            System.out.println("5. Tarjeta de débito");
            System.out.println("6. Tarjeta de crédito");
            System.out.print("Opción: ");
            int opcion = scanner.nextInt();

            // Verificar si el cliente ya tiene un producto financiero de este tipo
            switch (opcion) {
                case 1:
                    if (cliente.tieneProductoFinanciero(CuentaAhorros.class)) {
                        throw new BancoException("El cliente ya tiene una cuenta de ahorros");
                    }
                    System.out.print("Ingrese el saldo inicial de la cuenta de ahorros: ");
                    double saldoInicialAhorros = scanner.nextDouble();
                    System.out.print("Ingrese la tasa de interés de la cuenta de ahorros: ");
                    double tasaInteresAhorros = scanner.nextDouble();
                    cliente.agregarProducto(new CuentaAhorros(cliente, saldoInicialAhorros, tasaInteresAhorros));
                    break;
                case 2:
                    if (cliente.tieneProductoFinanciero(CuentaCorriente.class)) {
                        throw new BancoException("El cliente ya tiene una cuenta corriente");
                    }
                    System.out.print("Ingrese el saldo inicial de la cuenta corriente: ");
                    double saldoInicialCorriente = scanner.nextDouble();
                    System.out.print("Ingrese el sobregiro permitido de la cuenta corriente: ");
                    double sobregiroCorriente = scanner.nextDouble();
                    cliente.agregarProducto(new CuentaCorriente(cliente, saldoInicialCorriente, sobregiroCorriente));
                    break;
                case 3:
                    if (cliente.tieneProductoFinanciero(CreditoHipotecario.class)) {
                        throw new BancoException("El cliente ya tiene un crédito hipotecario");
                    }
                    System.out.print("Ingrese el monto del préstamo del crédito hipotecario: ");
                    double montoHipotecario = scanner.nextDouble();
                    if (montoHipotecario <= 0) {
                        throw new Exception("El monto del préstamo debe ser mayor que cero");
                    }
                    System.out.print("Ingrese la tasa de interés del crédito hipotecario: ");
                    double tasaInteresHipotecario = scanner.nextDouble();
                    cliente.agregarProducto(new CreditoHipotecario(cliente, montoHipotecario, tasaInteresHipotecario));
                    break;
                case 4:
                    if (cliente.tieneProductoFinanciero(CreditoLibreInversion.class)) {
                        throw new BancoException("El cliente ya tiene un crédito de libre inversión");
                    }
                    System.out.print("Ingrese el monto del préstamo del crédito de libre inversión: ");
                    double montoLibreInversion = scanner.nextDouble();
                    if (montoLibreInversion <= 0) {
                        throw new Exception("El monto del préstamo debe ser mayor que cero");
                    }
                    System.out.print("Ingrese la tasa de interés del crédito de libre inversión: ");
                    double tasaInteresLibreInversion = scanner.nextDouble();
                    cliente.agregarProducto(new CreditoLibreInversion(cliente, montoLibreInversion, tasaInteresLibreInversion));
                    break;
                case 5:
                    if (cliente.tieneProductoFinanciero(TarjetaDebito.class)) {
                        throw new BancoException("El cliente ya tiene una tarjeta de débito");
                    }
                    System.out.print("Ingrese el saldo inicial de la tarjeta de débito: ");
                    double saldoInicialDebito = scanner.nextDouble();
                    cliente.agregarProducto(new TarjetaDebito(cliente, saldoInicialDebito));
                    break;
                case 6:
                    if (cliente.tieneProductoFinanciero(TarjetaCredito.class)) {
                        throw new BancoException("El cliente ya tiene una tarjeta de crédito");
                    }
                    System.out.print("Ingrese el saldo de la tarjeta de crédito: ");
                    double saldoCredito = scanner.nextDouble();
                    if (saldoCredito <= 0) {
                        throw new Exception("El saldo de la tarjeta de crédito debe ser mayor que cero");
                    }
                    System.out.print("Ingrese la tasa de interés de la tarjeta de crédito: ");
                    double tasaInteresCredito = scanner.nextDouble();
                    cliente.agregarProducto(new TarjetaCredito(cliente, saldoCredito, tasaInteresCredito));
                    break;
                default:
                    throw new Exception("Opción no válida");
            }

            System.out.println("Producto financiero agregado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar producto financiero: " + e.getMessage());
        }
    }

    private Cliente buscarClientePorID(int id) {
        for (Cliente cliente : banco.getClientes()) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }
}
