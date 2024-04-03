public class PagoCreditoLibreInversion extends Transaccion {
    public PagoCreditoLibreInversion(double monto) {
        super("Pago de Crédito de Libre Inversión", monto);
    }

    @Override
    public void ejecutar(ProductoFinanciero producto) throws BancoException {
        if (!(producto instanceof CreditoLibreInversion)) {
            throw new BancoException("Tipo de producto financiero incorrecto para realizar el pago de crédito de libre inversión.");
        }

        CreditoLibreInversion creditoLibreInversion = (CreditoLibreInversion) producto;
        double deudaActual = creditoLibreInversion.getDeuda();
        double montoPago = this.getMonto();

        if (montoPago > deudaActual) {
            throw new BancoException("El monto a pagar excede la deuda actual del crédito de libre inversión.");
        }

        creditoLibreInversion.realizarPagoCredito(montoPago);
        System.out.println("Pago de $" + montoPago + " realizado exitosamente en el crédito de libre inversión.");
    }
}
