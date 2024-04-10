package Principal;

import Excepciones.BancoException;
import LogicaBanco.*;
import ProductosFinancieros.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
                throw new BancoException("Ya existe un cliente con ese ID"); // Lanza BancoException
            }

            scanner.nextLine();

            System.out.print("Ingrese el nombre del cliente: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el apellido del cliente: ");
            String apellido = scanner.nextLine();

            System.out.print("Ingrese el número de identificación del cliente: ");
            int numIdentificacion = scanner.nextInt();

            if (buscarClientePorIdentificacion(numIdentificacion) != null) {
                throw new BancoException("Ya existe un cliente con ese número de identificación"); // Lanza BancoException
            }

            System.out.print("Ingrese el número de teléfono del cliente: ");
            long numTelefono = scanner.nextLong();

            Cliente nuevoCliente = new Cliente(id, nombre, apellido, numIdentificacion, numTelefono);
            banco.agregarCliente(nuevoCliente);
            System.out.println("Cliente creado exitosamente.");
        } catch (BancoException e) { // Captura BancoException
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) { // Captura cualquier otra excepción
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
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
                Set<ProductoFinanciero> productosImpresos = new HashSet<>(); // Conjunto para evitar impresiones duplicadas
                for (ProductoFinanciero producto : cliente.getProductosFinancieros()) {
                    if (!productosImpresos.contains(producto)) {
                        System.out.println(producto.toString()); // Llama al método sobrescrito
                        System.out.println("\n");
                        productosImpresos.add(producto); // Agrega el producto al conjunto para evitar duplicados
                    }
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

            System.out.println("Productos financieros de " + cliente.getNombre() + " " + cliente.getApellido() + ":");
            List<ProductoFinanciero> productos = cliente.getProductosFinancieros();
            if (productos.isEmpty()) {
                throw new BancoException("El cliente no tiene productos financieros para realizar la consignación.");
            }

            Set<ProductoFinanciero> productosImpresos = new HashSet<>(); // Conjunto para evitar impresiones duplicadas

            for (int i = 0; i < productos.size(); i++) {
                ProductoFinanciero producto = productos.get(i);
                if (!productosImpresos.contains(producto)) {
                    System.out.println("[" + i + "] " + producto);
                    productosImpresos.add(producto); // Agregar el producto al conjunto
                }
            }

            System.out.print("Seleccione el número del producto financiero en el que desea consignar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            if (opcion < 0 || opcion >= productos.size()) {
                throw new BancoException("Opción inválida. Debe seleccionar un número válido.");
            }

            ProductoFinanciero producto = productos.get(opcion);

            System.out.print("Ingrese la cantidad a consignar: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer de entrada

            producto.consignar(cantidad);
            System.out.println("Se ha consignado exitosamente $" + cantidad + " en el producto financiero " + producto + " de " + cliente.getNombre() + " " + cliente.getApellido());
        } catch (BancoException e) {
            System.out.println("Error al consignar: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
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

            System.out.println("Productos financieros de " + cliente.getNombre() + " " + cliente.getApellido() + ":");
            List<ProductoFinanciero> productos = cliente.getProductosFinancieros();
            if (productos.isEmpty()) {
                throw new BancoException("El cliente no tiene productos financieros para realizar el retiro.");
            }

            Set<ProductoFinanciero> productosImpresos = new HashSet<>(); // Conjunto para evitar impresiones duplicadas

            for (int i = 0; i < productos.size(); i++) {
                ProductoFinanciero producto = productos.get(i);
                if (!productosImpresos.contains(producto)) {
                    System.out.println("[" + i + "] " + producto + "\n");
                    productosImpresos.add(producto); // Agregar el producto al conjunto
                }
            }

            System.out.print("Seleccione el número del producto financiero del que desea retirar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            if (opcion < 0 || opcion >= productos.size()) {
                throw new BancoException("Opción inválida. Debe seleccionar un número válido.");
            }

            ProductoFinanciero producto = productos.get(opcion);

            System.out.print("Ingrese la cantidad a retirar: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer de entrada

            producto.retirar(cantidad);
            System.out.println("Se ha retirado exitosamente $" + cantidad + " del producto financiero " + producto + " de " + cliente.getNombre() + " " + cliente.getApellido());
        } catch (BancoException e) {
            System.out.println("Error al retirar: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
        }
    }

    public void transferirSaldoEntreClientes(Scanner scanner) {
        try {
            System.out.println("Transferencia de saldo entre clientes");
            System.out.print("Ingrese el ID del cliente que envía: ");
            int idClienteOrigen = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            Cliente origen = buscarClientePorID(idClienteOrigen);
            if (origen == null) {
                throw new BancoException("No se encontró ningún cliente con el ID proporcionado para el cliente origen.");
            }

            System.out.println("Seleccione el producto financiero del cliente " + origen.getNombre() + " " + origen.getApellido() + ":");
            List<ProductoFinanciero> productosOrigen = origen.getProductosFinancieros();
            Set<ProductoFinanciero> productosImpresosOrigen = new HashSet<>(); // Conjunto para evitar impresiones duplicadas
            for (int i = 0; i < productosOrigen.size(); i++) {
                ProductoFinanciero producto = productosOrigen.get(i);
                if (!productosImpresosOrigen.contains(producto)) {
                    System.out.println((i + 1) + ". " + producto);
                    productosImpresosOrigen.add(producto); // Agregar el producto al conjunto
                }
            }
            System.out.print("Ingrese el número correspondiente al producto financiero: ");
            int indiceProductoOrigen = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            ProductoFinanciero productoOrigen = productosOrigen.get(indiceProductoOrigen - 1);

            System.out.print("Ingrese el ID del cliente que recibe: ");
            int idClienteDestino = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            Cliente destino = buscarClientePorID(idClienteDestino);
            if (destino == null) {
                throw new BancoException("No se encontró ningún cliente con el ID proporcionado para el cliente destino.");
            }

            System.out.println("Seleccione el producto financiero del cliente " + destino.getNombre() + " " + destino.getApellido() + ":");
            List<ProductoFinanciero> productosDestino = destino.getProductosFinancieros();
            Set<ProductoFinanciero> productosImpresosDestino = new HashSet<>(); // Conjunto para evitar impresiones duplicadas
            for (int i = 0; i < productosDestino.size(); i++) {
                ProductoFinanciero producto = productosDestino.get(i);
                if (!productosImpresosDestino.contains(producto)) {
                    System.out.println((i + 1) + ". " + producto);
                    productosImpresosDestino.add(producto); // Agregar el producto al conjunto
                }
            }
            System.out.print("Ingrese el número correspondiente al producto financiero: ");
            int indiceProductoDestino = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            ProductoFinanciero productoDestino = productosDestino.get(indiceProductoDestino - 1);

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
        } catch (BancoException e) {
            System.out.println("Error al transferir saldo entre clientes: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Ingrese un valor válido.");
            scanner.nextLine(); // Limpiar el buffer de entrada
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
