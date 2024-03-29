import java.util.ArrayList;

public class Banco {
    // Atributos
    private ArrayList<Cliente> clientes;
    private ArrayList<ProductoFinanciero> productosFinancieros;
    private ArrayList<Transaccion> transacciones;
    private ArrayList<ArchivoCSV> archivosCSV;
    
    // Constructor
    public Banco() {
        clientes = new ArrayList<>();
        productosFinancieros = new ArrayList<>();
        transacciones = new ArrayList<>();
        archivosCSV = new ArrayList<>();
    }
    
    // Métodos
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }
    
    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);
    }
    
    public Cliente buscarCliente(int idCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == idCliente) {
                return cliente;
            }
        }
        return null; // Cliente no encontrado
    }
    
    public void agregarProductoFinanciero(ProductoFinanciero producto) {
        productosFinancieros.add(producto);
    }
    
    public void eliminarProductoFinanciero(ProductoFinanciero producto) {
        productosFinancieros.remove(producto);
    }
    
    public ProductoFinanciero buscarProductoFinanciero(String numeroProducto) {
        for (ProductoFinanciero producto : productosFinancieros) {
            if (producto.getNumeroProducto().equals(numeroProducto)) {
                return producto;
            }
        }
        return null; // Producto financiero no encontrado
    }
    
    public void agregarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }
    
    public void eliminarTransaccion(Transaccion transaccion) {
        transacciones.remove(transaccion);
    }
    
    public void agregarArchivoCSV(ArchivoCSV archivo) {
        archivosCSV.add(archivo);
    }
    
    public void eliminarArchivoCSV(ArchivoCSV archivo) {
        archivosCSV.remove(archivo);
    }
}
