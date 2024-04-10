package LogicaBanco;

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
    public List<ProductoFinanciero> obtenerTodosLosProductosFinancieros() {
        List<ProductoFinanciero> productos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            productos.addAll(cliente.getProductosFinancieros());
        }
        return productos;
    }
}
