class TarjetaDebito extends ProductoFinanciero {
    private double saldo;

    public TarjetaDebito(int numeroCuenta, double saldo) {
        super(numeroCuenta);
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }
}