public class Seguro extends ProductoFinanciero {
    private String tipo;
    private double prima;

    public Seguro(int numeroCuenta, String tipo, double prima) {
        super(numeroCuenta);
        this.tipo = tipo;
        this.prima = prima;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrima() {
        return prima;
    }

    public void setPrima(double prima) {
        this.prima = prima;
    }
}