public abstract class ProductoFinanciero {
    
    private String numeroProducto;
    private double saldo;

    public ProductoFinanciero(String numeroProducto, double saldo) {
        this.numeroProducto = numeroProducto;
        this.saldo = saldo;
    }

    public abstract void realizarTransaccion(Transaccion transaccion);
    
    public String getNumeroProducto() {
        return numeroProducto;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setNumeroProducto(String numeroProducto) {
        this.numeroProducto = numeroProducto;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
