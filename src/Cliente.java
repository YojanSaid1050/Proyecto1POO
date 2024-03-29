import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nombre;
    private int numeroCuenta;
    private List<ProductoFinanciero> productos;
    private List<Transaccion> transacciones;

    public Cliente(String nombre, int numeroCuenta) {
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.productos = new ArrayList<>();
        this.transacciones = new ArrayList<>();
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

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public List<ProductoFinanciero> getProductos() {
        return productos;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }
}