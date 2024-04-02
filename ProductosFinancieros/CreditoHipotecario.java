public class CreditoHipotecario extends ProductoFinanciero {
    private double montoPrestamo;
    private double tasaInteres;
    private double deuda;

    public CreditoHipotecario(int numeroCuenta, double saldoInicial, double montoPrestamo, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.montoPrestamo = montoPrestamo;
        this.tasaInteres = tasaInteres;
        this.deuda = montoPrestamo + (tasaInteres * montoPrestamo);
    }

    public double getMontoPrestamo() {
        return montoPrestamo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }
    
    public double getDeuda() {
        return deuda;
    }
    
    public void realizarPagoCredito(double monto) throws BancoException {
        if (monto <= deuda) {
            deuda -= monto;
            System.out.println("Pago de $" + monto + " realizado. Deuda restante: $" + deuda);
        } else {
            throw new BancoException("El monto del pago excede la deuda actual.");
        }
    }
    

    @Override
    public String toString() {
        return "Crédito Hipotecario:\n" +
               "Monto del préstamo: $" + montoPrestamo + "\n" +
               "Tasa de interés: " + tasaInteres * 100 + "%\n" +
               "Deuda restante: $" + deuda;
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion.getTipo().equals("Pago de Crédito Hipotecario")) {
            double montoPago = transaccion.getMonto();
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
