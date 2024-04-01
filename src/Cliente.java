import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String nombre;
    private String apellido;
    private String id;
    private long numeroIdentificacion;
    private String telefono;
    private List<ProductoFinanciero> productos;
    private List<Transaccion> transacciones;
    private int numeroCuenta; // Número de cuenta asociado al cliente

    public Cliente(String nombre, String apellido, String id, String telefono, long numeroIdentificacion) throws BancoException {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.numeroIdentificacion = numeroIdentificacion;
        this.telefono = telefono;
        this.productos = new ArrayList<>();
        this.transacciones = new ArrayList<>();

        // Validar longitud del número de teléfono
        if (telefono.length() != 10) {
            throw new BancoException("El número de teléfono debe tener 10 dígitos.");
        }
    }

    public void agregarProducto(ProductoFinanciero producto) {
        productos.add(producto);
    }

    public void registrarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<ProductoFinanciero> getProductos() {
        return productos;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public ProductoFinanciero buscarProducto(int numeroCuentaProducto) {
        for (ProductoFinanciero producto : productos) {
            if (producto.getNumeroCuenta() == numeroCuentaProducto) {
                return producto;
            }
        }
        return null;
    }

    public boolean tieneTipoProducto(Class<?> tipoProducto) {
        for (ProductoFinanciero producto : productos) {
            if (tipoProducto.isInstance(producto)) {
                return true;
            }
        }
        return false;
    }

    public String obtenerProductosComoString() {
        StringBuilder sb = new StringBuilder();
        for (ProductoFinanciero producto : productos) {
            sb.append(producto.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Apellido: ").append(apellido).append("\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Número de Identificación: ").append(numeroIdentificacion).append("\n");
        sb.append("Teléfono: ").append(telefono).append("\n");
        sb.append("Productos Financieros: \n");
        for (ProductoFinanciero producto : productos) {
            sb.append("\t- ").append(producto).append(" - Número de cuenta: ").append(numeroCuenta).append("\n");
        }
        return sb.toString();
    }
}
