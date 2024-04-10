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

    public Cliente(int id, String nombre, String apellido, int numIdentificacion, long numTelefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numIdentificacion = numIdentificacion;
        this.numTelefono = numTelefono;
        this.productos = new ArrayList<>();
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
        productos.add(producto);
    }

    public List<ProductoFinanciero> getProductosFinancieros() {
        return productos;
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
