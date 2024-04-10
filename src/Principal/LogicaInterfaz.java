package Principal;

import Excepciones.BancoException;
import LogicaBanco.*;
import ProductosFinancieros.*;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LogicaInterfaz {
    private Banco banco;
    private AgregarProductoFinanciero agregarProductoFinanciero;

    public LogicaInterfaz(Banco banco) {
        this.banco = banco;
        this.agregarProductoFinanciero = new AgregarProductoFinanciero(banco);
    }

    public void crearCliente(Scanner scanner) {
        try {
            System.out.println("Creación de nuevo cliente");
            System.out.print("Ingrese el ID del cliente: ");
            int id = scanner.nextInt();

            if (buscarClientePorID(id) != null) {
                throw new BancoException("Ya existe un cliente con ese ID");
            }

            scanner.nextLine();

            System.out.print("Ingrese el nombre del cliente: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el apellido del cliente: ");
            String apellido = scanner.nextLine();

            System.out.print("Ingrese el número de identificación del cliente: ");
            int numIdentificacion = scanner.nextInt();

            if (buscarClientePorIdentificacion(numIdentificacion) != null) {
                throw new BancoException("Ya existe un cliente con ese número de identificación");
            }

            System.out.print("Ingrese el número de teléfono del cliente: ");
            long numTelefono = scanner.nextLong();

            Cliente nuevoCliente = new Cliente(id, nombre, apellido, numIdentificacion, numTelefono);
            banco.agregarCliente(nuevoCliente);
            System.out.println("Cliente creado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
        } catch (BancoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void agregarProductoFinanciero(Scanner scanner) {
        agregarProductoFinanciero.agregarProductoFinanciero(scanner);
    }

    void mostrarClientesProductosFinancieros() {
        Collection<Cliente> clientes = banco.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados en el banco.");
        } else {
            System.out.println("Información de los clientes y sus productos financieros:");
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Número de Identificación: " + cliente.getNumIdentificacion());
                System.out.println("Teléfono: " + cliente.getNumTelefono());
                System.out.println("Productos Financieros:\n");
                for (ProductoFinanciero producto : cliente.getProductosFinancieros()) {
                    System.out.println(producto.toString());
                    System.out.println("\n");
                }
            }
        }
    }

    public void consignar(Scanner scanner) {
        try {
            System.out.println("Consignación");
            System.out.print("Ingrese el ID del cliente: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            Cliente cliente = buscarClientePorID(idCliente);
            if (cliente == null) {
                throw new BancoException("No se encontró ningún cliente con el ID proporcionado.");
            }

            List<ProductoFinanciero> productos = cliente.getProductosFinancieros();
            if (productos.isEmpty()) {
                throw new BancoException("El cliente no tiene productos financieros para realizar la consignación.");
            }

            for (int i = 0; i < productos.size(); i++) {
                ProductoFinanciero producto = productos.get(i);
                System.out.println("[" + (i + 1) + "] " + producto);
            }

            System.out.print("Seleccione el número del producto financiero en el que desea consignar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            if (opcion < 1 || opcion > productos.size()) {
                throw new BancoException("Opción inválida. Debe seleccionar un número válido.");
            }

            ProductoFinanciero producto = productos.get(opcion - 1);

            System.out.print("Ingrese la cantidad a consignar: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer de entrada

            producto.consignar(cantidad);
            System.out.println("Se ha consignado exitosamente $" + cantidad + " en el producto financiero " + producto + " de " + cliente.getNombre() + " " + cliente.getApellido());
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
        } catch (BancoException e) {
            System.out.println("Error al consignar: " + e.getMessage());
        }
    }

    public void retirar(Scanner scanner) {
        try {
            System.out.println("Retiro");
            System.out.print("Ingrese el ID del cliente: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            Cliente cliente = buscarClientePorID(idCliente);
            if (cliente == null) {
                throw new BancoException("No se encontró ningún cliente con el ID proporcionado.");
            }

            List<ProductoFinanciero> productos = cliente.getProductosFinancieros();
            if (productos.isEmpty()) {
                throw new BancoException("El cliente no tiene productos financieros para realizar el retiro.");
            }

            for (int i = 0; i < productos.size(); i++) {
                ProductoFinanciero producto = productos.get(i);
                System.out.println("[" + (i + 1) + "] " + producto);
            }

            System.out.print("Seleccione el número del producto financiero del que desea retirar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            if (opcion < 1 || opcion > productos.size()) {
                throw new BancoException("Opción inválida. Debe seleccionar un número válido.");
            }

            ProductoFinanciero producto = productos.get(opcion - 1);

            System.out.print("Ingrese la cantidad a retirar: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer de entrada

            producto.retirar(cantidad);
            System.out.println("Se ha retirado exitosamente $" + cantidad + " del producto financiero " + producto + " de " + cliente.getNombre() + " " + cliente.getApellido());
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
        } catch (BancoException e) {
            System.out.println("Error al retirar: " + e.getMessage());
        }
    }

    public void transferirSaldoEntreClientes(Scanner scanner) throws BancoException {
        try {
            System.out.println("Transferencia de saldo entre clientes");

            // Solicitar ID del cliente que envía
            System.out.print("Ingrese el ID del cliente que envía: ");
            int idClienteOrigen = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            // Obtener el cliente que envía
            Cliente origen = buscarClientePorID(idClienteOrigen);
            if (origen == null) {
                throw new BancoException("No se encontró ningún cliente con el ID proporcionado para el cliente origen.");
            }
            if (origen.getProductosFinancieros().isEmpty()) {
                throw new BancoException("El cliente que envía no tiene productos financieros.");
            }

            // Mostrar los productos financieros del cliente que envía
            System.out.println("Seleccione el producto financiero del cliente " + origen.getNombre() + " " + origen.getApellido() + ":");
            List<ProductoFinanciero> productosOrigen = origen.getProductosFinancieros();
            for (int i = 0; i < productosOrigen.size(); i++) {
                ProductoFinanciero producto = productosOrigen.get(i);
                System.out.println((i + 1) + ". " + producto);
            }
            System.out.print("Ingrese el número correspondiente al producto financiero: ");
            int indiceProductoOrigen = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            if (indiceProductoOrigen < 1 || indiceProductoOrigen > productosOrigen.size()) {
                throw new BancoException("El número de producto seleccionado no es válido.");
            }
            ProductoFinanciero productoOrigen = productosOrigen.get(indiceProductoOrigen - 1);

            // Solicitar ID del cliente que recibe
            System.out.print("Ingrese el ID del cliente que recibe: ");
            int idClienteDestino = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            // Obtener el cliente que recibe
            Cliente destino = buscarClientePorID(idClienteDestino);
            if (destino == null) {
                throw new BancoException("No se encontró ningún cliente con el ID proporcionado para el cliente destino.");
            }
            if (destino.getProductosFinancieros().isEmpty()) {
                throw new BancoException("El cliente que recibe no tiene productos financieros.");
            }

            // Mostrar los productos financieros del cliente que recibe
            System.out.println("Seleccione el producto financiero del cliente " + destino.getNombre() + " " + destino.getApellido() + ":");
            List<ProductoFinanciero> productosDestino = destino.getProductosFinancieros();
            for (int i = 0; i < productosDestino.size(); i++) {
                ProductoFinanciero producto = productosDestino.get(i);
                System.out.println((i + 1) + ". " + producto);
            }
            System.out.print("Ingrese el número correspondiente al producto financiero: ");
            int indiceProductoDestino = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            if (indiceProductoDestino < 1 || indiceProductoDestino > productosDestino.size()) {
                throw new BancoException("El número de producto seleccionado no es válido.");
            }
            ProductoFinanciero productoDestino = productosDestino.get(indiceProductoDestino - 1);

            // Realizar la transferencia de saldo
            System.out.println("Saldo disponible en la cuenta de " + origen.getNombre() + " " + origen.getApellido() + ": $" + productoOrigen.consultarSaldo());
            System.out.print("Ingrese la cantidad a transferir: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer de entrada

            if (cantidad <= 0) {
                throw new BancoException("La cantidad a transferir debe ser mayor que cero.");
            }

            if (productoOrigen.consultarSaldo() < cantidad) {
                throw new BancoException("No hay suficiente saldo en la cuenta del cliente origen para realizar la transferencia.");
            }

            productoOrigen.transferir(productoDestino, cantidad);
            System.out.println("Se ha transferido exitosamente $" + cantidad + " del cliente " + origen.getNombre() + " " + origen.getApellido() +
                    " al cliente " + destino.getNombre() + " " + destino.getApellido());
        } catch (InputMismatchException e) {
            throw new BancoException("Error: Ingrese un valor válido.");
        }
    }
    public void transferirSaldoEntreCuentas(Scanner scanner) {
        try {
            System.out.println("Transferencia de saldo entre cuentas del mismo cliente");

            // Solicitar ID del cliente
            System.out.print("Ingrese el ID del cliente: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            // Obtener el cliente
            Cliente cliente = buscarClientePorID(idCliente);
            if (cliente == null) {
                throw new BancoException("No se encontró ningún cliente con el ID proporcionado.");
            }
            if (cliente.getProductosFinancieros().isEmpty()) {
                throw new BancoException("El cliente no tiene productos financieros para realizar la transferencia.");
            }

            // Mostrar los productos financieros del cliente
            System.out.println("Seleccione el producto financiero del cliente " + cliente.getNombre() + " " + cliente.getApellido() + ":");
            List<ProductoFinanciero> productos = cliente.getProductosFinancieros();
            for (int i = 0; i < productos.size(); i++) {
                ProductoFinanciero producto = productos.get(i);
                System.out.println((i + 1) + ". " + producto);
            }
            System.out.print("Ingrese el número correspondiente al producto financiero de origen: ");
            int indiceProductoOrigen = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            if (indiceProductoOrigen < 1 || indiceProductoOrigen > productos.size()) {
                throw new BancoException("El número de producto seleccionado no es válido.");
            }
            ProductoFinanciero productoOrigen = productos.get(indiceProductoOrigen - 1);

            // Solicitar el número del producto financiero destino
            System.out.print("Ingrese el número correspondiente al producto financiero de destino: ");
            int indiceProductoDestino = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            if (indiceProductoDestino < 1 || indiceProductoDestino > productos.size()) {
                throw new BancoException("El número de producto seleccionado no es válido.");
            }
            ProductoFinanciero productoDestino = productos.get(indiceProductoDestino - 1);

            // Verificar que los productos financieros sean diferentes
            if (productoOrigen.equals(productoDestino)) {
                throw new BancoException("No se puede transferir saldo entre el mismo producto financiero.");
            }

            // Realizar la transferencia de saldo
            System.out.println("Saldo disponible en la cuenta origen: $" + productoOrigen.consultarSaldo());
            System.out.print("Ingrese la cantidad a transferir: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer de entrada

            if (cantidad <= 0) {
                throw new BancoException("La cantidad a transferir debe ser mayor que cero.");
            }

            if (productoOrigen.consultarSaldo() < cantidad) {
                throw new BancoException("No hay suficiente saldo en la cuenta de origen para realizar la transferencia.");
            }

            productoOrigen.transferir(productoDestino, cantidad);
            System.out.println("Se ha transferido exitosamente $" + cantidad + " del producto financiero " + productoOrigen +
                    " al producto financiero " + productoDestino + " del cliente " + cliente.getNombre() + " " + cliente.getApellido());
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
        } catch (BancoException e) {
            System.out.println("Error al realizar la transferencia: " + e.getMessage());
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

    private Cliente buscarClientePorIdentificacion(int numIdentificacion) {
        for (Cliente cliente : banco.getClientes()) {
            if (cliente.getNumIdentificacion() == numIdentificacion) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> getListaClientes() {
        return banco.getClientes();
    }
}
