package LogicaBanco;



public abstract class ProductoFinanciero {
    protected int numeroCuenta;
    protected double saldo;

    public ProductoFinanciero(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0.0; // Saldo inicializado a 0 por defecto
    }

    public ProductoFinanciero(int numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    // Método abstracto para procesar transacciones específicas de cada tipo de producto financiero
    public abstract void procesarTransaccion(Transaccion transaccion);
    @Override
    public abstract String toString();
}
