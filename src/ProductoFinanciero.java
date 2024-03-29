public abstract class ProductoFinanciero {
    private int numeroCuenta;

    public ProductoFinanciero(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public abstract void realizarConsignacion(double monto);

    public abstract void realizarRetiro(double monto);

    public abstract void realizarPago(double monto);

    public abstract void realizarTransferencia(int cuentaDestino, double monto);

    public abstract void realizarPagoTarjetaCredito(double monto);

    public int getNumeroCuenta() {
        return numeroCuenta;
    }
}