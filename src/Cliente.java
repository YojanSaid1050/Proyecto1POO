import java.util.ArrayList;
import java.util.Iterator;
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
    
    public ProductoFinanciero obtenerProductoPorTipo(Class<?> tipo) {
        for (ProductoFinanciero producto : productos) {
            if (tipo.isInstance(producto)) {
                return producto;
            }
        }
        return null; // O lanzar una excepción si se desea un manejo más estricto
    }
    public void eliminarProducto(CreditoHipotecario credito) {
        // Verificar si la lista de productos está inicializada
        if (productos == null) {
            return; // O lanzar una excepción si es necesario
        }

        // Utilizar un iterador para recorrer la lista y eliminar el producto
        Iterator<ProductoFinanciero> iter = productos.iterator();
        while (iter.hasNext()) {
            ProductoFinanciero producto = iter.next();
            if (producto instanceof CreditoHipotecario && producto.equals(credito)) {
                iter.remove(); // Eliminar el producto de la lista
                return; // Terminar el método una vez que se ha eliminado el producto
            }
        }
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
    public CuentaAhorros obtenerCuentaAhorros() {
        // Iterar sobre los productos financieros del cliente para encontrar la cuenta de ahorros
        for (ProductoFinanciero producto : productos) {
            if (producto instanceof CuentaAhorros) {
                return (CuentaAhorros) producto;
            }
        }
        // Si no se encuentra ninguna cuenta de ahorros, devolver null
        return null;
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
