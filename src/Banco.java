import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class Banco {

    private Map<Integer, Cliente> clientes; // HashMap con el número de cuenta como clave

    public Banco() {
        clientes = new HashMap<>();
    }

    public void agregarCliente(Cliente cliente) throws BancoException {
        if (!clientes.containsValue(cliente)) {
            int numeroCuenta = getNextNumeroCuenta();
            cliente.setNumeroCuenta(numeroCuenta);
            clientes.put(numeroCuenta, cliente);
            System.out.println("Cliente agregado correctamente. Su número de cuenta es: " + numeroCuenta);
        } else {
            throw new BancoException("El cliente ya existe en el banco.");
        }
    }

    public Cliente buscarCliente(String idCliente) {
        for (Cliente cliente : clientes.values()) {
            if (cliente.getId().equals(idCliente)) {
                return cliente;
            }
        }
        return null;
    }

    public void eliminarCliente(int numeroCuenta) throws BancoException {
        if (clientes.containsKey(numeroCuenta)) {
            clientes.remove(numeroCuenta);
            System.out.println("Cliente eliminado correctamente.");
        } else {
            throw new BancoException("El cliente no existe en el banco.");
        }
    }

    public void actualizarCliente(int numeroCuenta, Cliente cliente) throws BancoException {
        if (clientes.containsKey(numeroCuenta)) {
            clientes.put(numeroCuenta, cliente);
            System.out.println("Cliente actualizado correctamente.");
        } else {
            throw new BancoException("El cliente no existe en el banco.");
        }
    }

    public void listarClientes() {
        System.out.println("Clientes del banco:");
        for (Cliente cliente : clientes.values()) {
            System.out.println(cliente);
        }
    }

    public Collection<Cliente> getClientes() {
        return clientes.values();
    }

    private int getNextNumeroCuenta() {
        // Implementación para generar el siguiente número de cuenta
        // Puedes modificar esta lógica según tus requisitos
        // Por ejemplo, puedes usar una base inicial y luego incrementarla para cada nuevo cliente
        return clientes.size() + 1000;
    }
}
