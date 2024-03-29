public class CuentaAhorros extends ProductoFinanciero {
    private boolean conectadaTarjetaDebito;
    private double tasaInteres;
    private double saldo;

    public CuentaAhorros(int numeroCuenta, double saldoInicial, boolean conectadaTarjetaDebito) {
        super(numeroCuenta, saldoInicial);
        this.conectadaTarjetaDebito = conectadaTarjetaDebito;
    }

    public boolean isConectadaTarjetaDebito() {
        return conectadaTarjetaDebito;
    }
    public double getTasaInteres() {
        return this.tasaInteres;
    }
    public CuentaAhorros(int numeroCuenta, double tasaInteres, double saldoInicial) {
        super(numeroCuenta);
        this.tasaInteres = tasaInteres;
        this.saldo = saldoInicial;
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Retiro) {
            Retiro retiro = (Retiro) transaccion;
            double montoRetirado = retiro.getMonto();
            if (montoRetirado > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta de ahorros.");
            }
            this.saldo -= montoRetirado;
            System.out.println("Se ha retirado $" + montoRetirado + " del saldo de la cuenta de ahorros.");
        } else if (transaccion.getTipo().equals("Añadir Saldo")) {
            double montoAñadido = transaccion.getMonto();
            this.saldo += montoAñadido;
            System.out.println("Se ha añadido $" + montoAñadido + " al saldo de la cuenta de ahorros.");
        } else if (transaccion.getTipo().equals("Pago de Crédito de Libre Inversión") ||
                transaccion.getTipo().equals("Pago de Crédito Hipotecario") ||
                transaccion.getTipo().equals("Pago de Tarjeta de Crédito") ||
                transaccion.getTipo().equals("Pago de Seguro")) {
            double montoPago = transaccion.getMonto();
            if (montoPago > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta de ahorros para realizar el pago.");
            }
            this.saldo -= montoPago;
            System.out.println("Se ha realizado un pago de $" + montoPago + " desde la cuenta de ahorros.");
        } else if (transaccion.getTipo().equals("Pago de Servicio Público")) {
            PagoServicioPublico pagoServicio = (PagoServicioPublico) transaccion;
            double monto = pagoServicio.getMonto();
            String tipoServicio = pagoServicio.getTipoServicioPublico();
            String numeroReferencia = pagoServicio.getNumeroReferencia();
            
            if (monto > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta de ahorros para pagar el servicio público.");
            }
            
            this.saldo -= monto;
            System.out.println("Se ha pagado $" + monto + " del servicio público '" + tipoServicio + "' con número de referencia '" + numeroReferencia + "' desde la cuenta de ahorros.");
        } else if (transaccion.getTipo().equals("Transferencia")) {
            Transferencia transferencia = (Transferencia) transaccion;
            double montoTransferencia = transferencia.getMonto();
            int numeroCuentaDestino = transferencia.getNumeroCuentaDestino();
            String idClienteDestino = transferencia.getIdClienteDestino();
            String nombreClienteDestino = transferencia.getNombreClienteDestino();
            
            if (montoTransferencia > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta de ahorros para realizar la transferencia.");
            }
            
            this.saldo -= montoTransferencia;
            System.out.println("Se ha transferido $" + montoTransferencia + " a la cuenta con número " + numeroCuentaDestino + " del cliente " + nombreClienteDestino + " (ID: " + idClienteDestino + ")");
        } else {
            System.out.println("Tipo de transacción no válida para la cuenta de ahorros.");
        }
    }
}
