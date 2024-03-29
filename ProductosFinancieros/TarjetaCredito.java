public class TarjetaCredito extends ProductoFinanciero {
    private double limiteCredito;
    private double saldoDisponible;

    public TarjetaCredito(int numeroCuenta, double limiteCredito) {
        super(numeroCuenta);
        this.limiteCredito = limiteCredito;
        this.saldoDisponible = limiteCredito;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }
}
