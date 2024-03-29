class CuentaAhorros extends ProductoFinanciero {
    private double tasaInteres;
    private double saldo;

    public CuentaAhorros(int numeroCuenta, double tasaInteres, double saldo) {
        super(numeroCuenta);
        this.tasaInteres = tasaInteres;
        this.saldo = saldo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public double getSaldo() {
        return saldo;
    }
}