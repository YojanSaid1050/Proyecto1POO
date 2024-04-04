package LogicaBanco;


import java.util.ArrayList;
import java.util.List;

import Excepciones.BancoException;

public class Cliente {

    private String nombre;
    private String apellido;
    private String id;
    private long numeroIdentificacion;
    private String telefono;
    private List<ProductoFinanciero> productos;
    private int numeroCuenta; // Número de cuenta asociado al cliente

    public Cliente(String nombre, String apellido, String id, String telefono, long numeroIdentificacion) throws BancoException {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.numeroIdentificacion = numeroIdentificacion;
        this.telefono = telefono;
        this.productos = new ArrayList<>();

        // Validar longitud del número de teléfono
        if (telefono.length() != 10) {
            throw new BancoException("El número de teléfono debe tener 10 dígitos.");
        }
    }

    // Métodos para agregar y eliminar productos financieros
    public void agregarProducto(ProductoFinanciero producto) {
        productos.add(producto);
    }

    public void eliminarProducto(ProductoFinanciero producto) {
        productos.remove(producto);
    }

    // Métodos para buscar productos financieros
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
        return null;
    }

    // Método para obtener una lista de todos los productos financieros del cliente
    public List<ProductoFinanciero> getProductos() {
        return new ArrayList<>(productos);
    }

    // Otros métodos de acceso
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

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public boolean tieneTipoProducto(Class<?> tipo) {
        for (ProductoFinanciero producto : productos) {
            if (tipo.isInstance(producto)) {
                return true;
            }
        }
        return false;
    }

    // Método para representar el cliente como una cadena de texto
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Información del cliente:\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Apellido: ").append(apellido).append("\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Número de Identificación: ").append(numeroIdentificacion).append("\n");
        sb.append("Teléfono: ").append(telefono).append("\n");
        sb.append("Número de Cuenta: ").append(numeroCuenta).append("\n");
        sb.append("\n");
        sb.append("Productos Financieros:\n");
        for (ProductoFinanciero producto : productos) {
            sb.append("\t- ").append(producto).append("\n");
        }
        return sb.toString();
    }

}
