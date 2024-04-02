public class CuentaAhorros extends ProductoFinanciero {
    private double tasaInteres;

    public CuentaAhorros(int numeroCuenta, double saldoInicial, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Consignacion) {
            Consignacion consignacion = (Consignacion) transaccion;
            double montoConsignado = consignacion.getMonto();
            this.saldo += montoConsignado;
            System.out.println("Se ha consignado $" + montoConsignado + " en la cuenta de ahorros. Nuevo saldo: $" + this.saldo);
        } else if (transaccion instanceof PagoCreditoHipotecario) {
            PagoCreditoHipotecario pagoCredito = (PagoCreditoHipotecario) transaccion;
            double montoPago = pagoCredito.getMonto();
            if (montoPago <= this.saldo) {
                this.saldo -= montoPago;
                System.out.println("Se ha realizado un pago de $" + montoPago + " desde la cuenta de ahorros. Nuevo saldo: $" + this.saldo);
            } else {
                System.out.println("Error: Saldo insuficiente en la cuenta de ahorros para realizar el pago.");
            }
        } else {
            System.out.println("Tipo de transacción no válida para la cuenta de ahorros.");
        }
    }

    @Override
    public String toString() {
        return "Cuenta de Ahorros:\n" +
               "Número de cuenta: " + getNumeroCuenta() + "\n" +
               "Saldo: $" + getSaldo() + "\n" +
               "Tasa de interés: " + (tasaInteres * 100) + "%";
    }
}
