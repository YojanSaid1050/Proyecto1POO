public class CuentaCorriente extends ProductoFinanciero {
    private double sobregiroPermitido;

    public CuentaCorriente(int numeroCuenta, double saldoInicial, double sobregiroPermitido) {
        super(numeroCuenta, saldoInicial);
        this.sobregiroPermitido = sobregiroPermitido;
    }

    public double getSobregiroPermitido() {
        return sobregiroPermitido;
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Consignacion) {
            Consignacion consignacion = (Consignacion) transaccion;
            double montoConsignado = consignacion.getMonto();
            this.saldo += montoConsignado;
            System.out.println("Se ha consignado $" + montoConsignado + " en la cuenta corriente. Nuevo saldo: $" + this.saldo);
        } else if (transaccion instanceof PagoCreditoHipotecario) {
            PagoCreditoHipotecario pagoCredito = (PagoCreditoHipotecario) transaccion;
            double montoPago = pagoCredito.getMonto();
            if (montoPago <= (this.saldo + sobregiroPermitido)) {
                this.saldo -= montoPago;
                System.out.println("Se ha realizado un pago de $" + montoPago + " desde la cuenta corriente. Nuevo saldo: $" + this.saldo);
            } else {
                System.out.println("Error: Saldo insuficiente en la cuenta corriente para realizar el pago.");
            }
        } else {
            System.out.println("Tipo de transacción no válida para la cuenta corriente.");
        }
    }

    @Override
    public String toString() {
        return "Cuenta Corriente:\n" +
               "Número de cuenta: " + getNumeroCuenta() + "\n" +
               "Saldo: $" + getSaldo() + "\n" +
               "Sobregiro permitido: $" + sobregiroPermitido;
    }
}
