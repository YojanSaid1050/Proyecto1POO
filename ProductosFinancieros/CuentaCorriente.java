class CuentaCorriente extends ProductoFinanciero {
    private double sobregiroPermitido;
    private double saldoDisponible;

    public CuentaCorriente(int numeroCuenta, double sobregiroPermitido, double saldoDisponible) {
        super(numeroCuenta);
        this.sobregiroPermitido = sobregiroPermitido;
        this.saldoDisponible = saldoDisponible;
    }

    public double getSobregiroPermitido() {
        return sobregiroPermitido;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }
}