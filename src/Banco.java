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
            // Generar y asignar el número de cuenta al cliente solo si no existe previamente
            cliente.setNumeroCuenta(getNextNumeroCuenta());
            clientes.put(cliente.getId(), cliente);
            System.out.println("Cliente agregado correctamente. Su número de cuenta es: " + cliente.getNumeroCuenta());
        } else {
            throw new BancoException("El cliente ya existe en el banco.");
        }
    }

    public Cliente buscarCliente(String id) {
        return clientes.get(id); // Busca por ID en el HashMap
    }

    public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientes.values());
    }

    int getNextNumeroCuenta() {
        // Implementación para generar el siguiente número de cuenta
        // Puedes modificar esta lógica según tus requisitos
        // Por ejemplo, puedes usar una base inicial y luego incrementarla para cada nuevo cliente
        return clientes.size() + 1000;
    }
}
