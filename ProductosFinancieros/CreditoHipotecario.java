public class CreditoHipotecario extends ProductoFinanciero {
    private double montoPrestamo;
    private double tasaInteres;
    private double deuda;
    private boolean retirado;

    public CreditoHipotecario(int numeroCuenta, double saldoInicial, double montoPrestamo, double tasaInteres) {
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

    @Override
    public String toString() {
        return "Crédito Hipotecario:\n" +
               "Saldo: $" + getSaldo() + "\n" +
               "Monto del préstamo: $" + montoPrestamo + "\n" +
               "Tasa de interés: " + tasaInteres + "%\n" +
               "Deuda restante: $" + deuda + "\n" +
               "Retirado: " + (retirado ? "Sí" : "No");
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
            System.out.println("Retiro de $" + montoRetiro + " realizado con éxito desde el crédito hipotecario.");
        } else if (transaccion.getTipo().equals("Pago de Crédito Hipotecario")) {
            if (!retirado) {
                throw new IllegalStateException("No se puede pagar el crédito antes de realizar un retiro.");
            }
            double montoPago = (montoPrestamo * (1 + tasaInteres));
            if (montoPago > deuda) {
                montoPago = deuda;
            }
            this.saldo -= montoPago;
            deuda -= montoPago;
            System.out.println("Pago de $" + montoPago + " realizado con éxito para el crédito hipotecario. Deuda restante: $" + deuda);
        } else {
            System.out.println("Tipo de transacción no válida para el crédito hipotecario.");
        }
    }
}
