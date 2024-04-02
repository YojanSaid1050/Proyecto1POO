public class TarjetaDebito extends ProductoFinanciero {
    public TarjetaDebito(int numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Consignacion) {
            Consignacion consignacion = (Consignacion) transaccion;
            double montoConsignado = consignacion.getMonto();
            this.saldo += montoConsignado;
            System.out.println("Se ha consignado $" + montoConsignado + " en la tarjeta de débito. Nuevo saldo: $" + this.saldo);
        } else if (transaccion instanceof PagoCreditoHipotecario) {
            PagoCreditoHipotecario pagoCredito = (PagoCreditoHipotecario) transaccion;
            double montoPago = pagoCredito.getMonto();
            if (montoPago <= this.saldo) {
                this.saldo -= montoPago;
                System.out.println("Se ha realizado un pago de $" + montoPago + " desde la tarjeta de débito. Nuevo saldo: $" + this.saldo);
            } else {
                System.out.println("Error: Saldo insuficiente en la tarjeta de débito para realizar el pago.");
            }
        } else {
            System.out.println("Tipo de transacción no válida para la tarjeta de débito.");
        }
    }

    @Override
    public String toString() {
        return "Tarjeta de Débito:\n" +
               "Número de cuenta: " + getNumeroCuenta() + "\n" +
               "Saldo: $" + getSaldo();
    }
}
