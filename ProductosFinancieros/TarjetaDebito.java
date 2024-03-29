public class TarjetaDebito extends ProductoFinanciero {
    public TarjetaDebito(int numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Retiro) {
            Retiro retiro = (Retiro) transaccion;
            double montoRetirado = retiro.getMonto();
            if (montoRetirado > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la tarjeta de débito.");
            }
            this.saldo -= montoRetirado;
            System.out.println("Se ha retirado $" + montoRetirado + " del saldo de la tarjeta de débito.");
        } else if (transaccion.getTipo().equals("Añadir Saldo")) {
            double montoAñadido = transaccion.getMonto();
            this.saldo += montoAñadido;
            System.out.println("Se ha añadido $" + montoAñadido + " al saldo de la tarjeta de débito.");
        } else if (transaccion.getTipo().equals("Pago de Crédito de Libre Inversión") ||
                transaccion.getTipo().equals("Pago de Crédito Hipotecario") ||
                transaccion.getTipo().equals("Pago de Tarjeta de Crédito") ||
                transaccion.getTipo().equals("Pago de Seguro")) {
            double montoPago = transaccion.getMonto();
            if (montoPago > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la tarjeta de débito para realizar el pago.");
            }
            this.saldo -= montoPago;
            System.out.println("Se ha realizado un pago de $" + montoPago + " desde la tarjeta de débito.");
        } else if (transaccion.getTipo().equals("Pago de Servicio Público")) {
            PagoServicioPublico pagoServicio = (PagoServicioPublico) transaccion;
            double monto = pagoServicio.getMonto();
            String tipoServicio = pagoServicio.getTipoServicioPublico();
            String numeroReferencia = pagoServicio.getNumeroReferencia();
            
            if (monto > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la tarjeta de débito para pagar el servicio público.");
            }
            
            this.saldo -= monto;
            System.out.println("Se ha pagado $" + monto + " del servicio público '" + tipoServicio + "' con número de referencia '" + numeroReferencia + "' desde la tarjeta de débito.");
        } else {
            System.out.println("Tipo de transacción no válida para la tarjeta de débito.");
        }
    }
}
