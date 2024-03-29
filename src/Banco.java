import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Banco {

    private Map<String, Cliente> clientes; // HashMap con ID como clave

    public Banco() {
        clientes = new HashMap<>();
    }

    public void agregarCliente(Cliente cliente) throws BancoException {
        if (!clientes.containsKey(cliente.getId())) {
            clientes.put(cliente.getId(), cliente);
            System.out.println("Cliente agregado correctamente.");
        } else {
            throw new BancoException("El cliente ya existe en el banco.");
        }
    }

    public Cliente buscarCliente(String id) {
        return clientes.get(id); // Busca por ID en el HashMap
    }

    public Cliente buscarClientePorId(String id) throws BancoException {
        Cliente cliente = buscarCliente(id);
        if (cliente == null) {
            throw new BancoException("Cliente no encontrado en el banco.");
        }
        return cliente;
    }

    public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientes.values());
    }

    public void realizarTransferencia(String idClienteOrigen, int numeroCuentaOrigen, double monto, int numeroCuentaDestino, String idClienteDestino, String nombreClienteDestino) {
        Cliente clienteOrigen = buscarCliente(idClienteOrigen);
        if (clienteOrigen == null) {
            System.out.println("Cliente no encontrado en el banco.");
            return;
        }

        ProductoFinanciero cuentaOrigen = clienteOrigen.buscarProducto(numeroCuentaOrigen);
        if (cuentaOrigen == null) {
            System.out.println("Cuenta de origen no encontrada para el cliente.");
            return;
        }

        if (!(cuentaOrigen instanceof CuentaAhorros) && !(cuentaOrigen instanceof CuentaCorriente)) {
            System.out.println("La cuenta de origen no es válida para transferencias.");
            return;
        }

        Transaccion transferencia = new Transferencia(monto, numeroCuentaDestino, idClienteDestino, nombreClienteDestino);
        cuentaOrigen.procesarTransaccion(transferencia);
    }
}
