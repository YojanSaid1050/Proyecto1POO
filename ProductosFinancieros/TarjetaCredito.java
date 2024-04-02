public class TarjetaCredito extends ProductoFinanciero {
    private double limiteCredito;
    private double tasaInteres;

    public TarjetaCredito(int numeroCuenta, double saldoInicial, double limiteCredito, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.limiteCredito = limiteCredito;
        this.tasaInteres = tasaInteres;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public String toString() {
        return "Tarjeta de Crédito\n" +
               "Límite de crédito: $" + limiteCredito + "\n" +
               "Tasa de interés: " + tasaInteres * 100 + "%";
    }

	@Override
	public void procesarTransaccion(Transaccion transaccion) {
		// TODO Auto-generated method stub
		
	}
}
