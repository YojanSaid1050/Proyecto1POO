public class Cliente {

    private int idCliente;
    private String nombre;
    private String direccion;
    private int telefono;
    
    public Cliente(int idCliente, String nombre, String direccion, int telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public int getTelefono() {
        return telefono;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
