public class CreditoLibreInversion extends ProductoFinanciero {
    private double montoPrestamo;
    private double tasaInteres;
    private double deuda;
    private boolean retirado;

    public CreditoLibreInversion(int numeroCuenta, double saldoInicial, double montoPrestamo, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.montoPrestamo = montoPrestamo;
        this.tasaInteres = tasaInteres;
        this.deuda = montoPrestamo;
        this.retirado = false;
    }

    public double getMontoPrestamo() {
        return montoPrestamo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public double getDeudaRestante() {
        return deuda;
    }

    public boolean isRetirado() {
        return retirado;
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion.getTipo().equals("Retiro")) {
            if (retirado) {
                throw new IllegalStateException("Ya se ha realizado un retiro previamente. Pague el crédito antes de retirar nuevamente.");
            }
            double montoRetiro = transaccion.getMonto();
            this.saldo += montoRetiro;
            retirado = true;
            System.out.println("Retiro de $" + montoRetiro + " realizado con éxito desde el crédito de libre inversión.");
        } else if (transaccion.getTipo().equals("Pago de Crédito de Libre Inversión")) {
            if (!retirado) {
                throw new IllegalStateException("No se puede pagar el crédito antes de realizar un retiro.");
            }
            double montoPago = (montoPrestamo * (1 + tasaInteres));
            if (montoPago > deuda) {
                montoPago = deuda;
            }
            this.saldo -= montoPago;
            deuda -= montoPago;
            System.out.println("Pago de $" + montoPago + " realizado con éxito para el crédito de libre inversión. Deuda restante: $" + deuda);
        } else {
            System.out.println("Tipo de transacción no válida para el crédito de libre inversión.");
        }
    }

    @Override
    public String toString() {
        return "Crédito de Libre Inversión:\n" +
               "Saldo: $" + getSaldo() + "\n" +
               "Monto del préstamo: $" + montoPrestamo + "\n" +
               "Tasa de interés: " + tasaInteres * 100 + "%\n" +
               "Deuda restante: $" + deuda + "\n" +
               "Retirado: " + (retirado ? "Sí" : "No");
    }
}