public abstract class ProductoFinanciero {
    protected int numeroCuenta;
    protected double saldo;

    public ProductoFinanciero(int numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }
    public ProductoFinanciero(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public abstract void procesarTransaccion(Transaccion transaccion);
}
