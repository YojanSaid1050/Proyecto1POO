import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getNumeroCuenta() == id) {
                return cliente;
            }
        }
        return null;
    }

    public void transferir(int cuentaOrigen, int cuentaDestino, double monto) {
        Cliente clienteOrigen = buscarClientePorId(cuentaOrigen);
        Cliente clienteDestino = buscarClientePorId(cuentaDestino);

        if (clienteOrigen == null || clienteDestino == null) {
            System.out.println("Error: Cuenta de origen o destino no encontrada.");
            return;
        }

        for (ProductoFinanciero productoOrigen : clienteOrigen.getProductos()) {
            if (productoOrigen instanceof CuentaAhorros || productoOrigen instanceof CuentaCorriente) {
                productoOrigen.realizarTransferencia(cuentaDestino, monto);
                break;
            }
        }
    }
}