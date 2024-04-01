public class Seguro extends ProductoFinanciero {
    private String tipoSeguro;
    private double primaSeguro;

    public Seguro(int numeroCuenta, double saldoInicial, String tipoSeguro, double primaSeguro) {
        super(numeroCuenta, saldoInicial);
        this.tipoSeguro = tipoSeguro;
        this.primaSeguro = primaSeguro;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public double getPrimaSeguro() {
        return primaSeguro;
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        // Implementar lógica específica para transacciones de seguro
    }

    @Override
    public String toString() {
        return "Seguro: " + tipoSeguro + "\n" +
               "Prima del seguro: " + primaSeguro + "\n" +
               "Saldo: " + getSaldo();
    }
}
