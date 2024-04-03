public class PagoCreditoHipotecario extends Transaccion {
    public PagoCreditoHipotecario(double monto) {
        super("Pago de Crédito Hipotecario", monto);
    }

    @Override
    public void ejecutar(ProductoFinanciero producto) throws BancoException {
        if (!(producto instanceof CreditoHipotecario)) {
            throw new BancoException("Tipo de producto financiero incorrecto para realizar el pago de crédito hipotecario.");
        }

        CreditoHipotecario creditoHipotecario = (CreditoHipotecario) producto;
        double deudaActual = creditoHipotecario.getDeuda();
        double montoPago = this.getMonto();

        if (deudaActual < montoPago) {
            throw new BancoException("El monto a pagar excede la deuda actual del crédito hipotecario.");
        }

        creditoHipotecario.realizarPagoCredito(montoPago);
        System.out.println("Pago de $" + montoPago + " realizado exitosamente en el crédito hipotecario.");
    }
}
