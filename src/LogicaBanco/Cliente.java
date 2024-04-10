package LogicaBanco;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private int numIdentificacion;
    private long numTelefono;
    private List<ProductoFinanciero> productos;
    private List<String> transacciones; // Lista para almacenar transacciones

    public Cliente(int id, String nombre, String apellido, int numIdentificacion, long numTelefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numIdentificacion = numIdentificacion;
        this.numTelefono = numTelefono;
        this.productos = new ArrayList<>();
        this.transacciones = new ArrayList<>(); // Inicializar la lista de transacciones
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getNumIdentificacion() {
        return numIdentificacion;
    }

    public long getNumTelefono() {
        return numTelefono;
    }

    public void agregarProducto(ProductoFinanciero producto) {
        if (!productos.contains(producto)) {
            productos.add(producto);
        }
    }

    public boolean tieneProductoFinanciero(Class<? extends ProductoFinanciero> tipoProducto) {
        for (ProductoFinanciero producto : productos) {
            if (tipoProducto.isInstance(producto)) {
                return true;
            }
        }
        return false;
    }
    
    public List<ProductoFinanciero> getProductosFinancieros() {
        return productos;
    }

    // Método para agregar una transacción a la lista
    public void agregarTransaccion(String transaccion) {
        transacciones.add(transaccion);
    }

    // Método para obtener todas las transacciones
    public List<String> getTransacciones() {
        return transacciones;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Cliente \n");
        builder.append("  ID: ").append(id).append("\n");
        builder.append("  Nombre: ").append(nombre).append("\n");
        builder.append("  Apellido: ").append(apellido).append("\n");
        builder.append("  Número de Identificación: ").append(numIdentificacion).append("\n");
        builder.append("  Número de Teléfono: ").append(numTelefono).append("\n");
        return builder.toString();
    }
}
