public class CuentaCorriente extends ProductoFinanciero {
    private double sobregiroPermitido;

    public CuentaCorriente(int numeroCuenta, double saldoInicial, double sobregiroPermitido) {
        super(numeroCuenta, saldoInicial);
        this.sobregiroPermitido = sobregiroPermitido;
    }

    public double getSobregiroPermitido() {
        return sobregiroPermitido;
    }

    public double getSaldoDisponible() {
        return this.saldo + this.sobregiroPermitido;
    }
    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Retiro) {
            Retiro retiro = (Retiro) transaccion;
            double montoRetirado = retiro.getMonto();
            if (montoRetirado > this.saldo + sobregiroPermitido) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta corriente.");
            }
            this.saldo -= montoRetirado;
            System.out.println("Se ha retirado $" + montoRetirado + " del saldo de la cuenta corriente.");
        } else if (transaccion.getTipo().equals("Añadir Saldo")) {
            double montoAñadido = transaccion.getMonto();
            this.saldo += montoAñadido;
            System.out.println("Se ha añadido $" + montoAñadido + " al saldo de la cuenta corriente.");
        } else if (transaccion.getTipo().equals("Pago de Crédito de Libre Inversión") ||
                transaccion.getTipo().equals("Pago de Crédito Hipotecario") ||
                transaccion.getTipo().equals("Pago de Tarjeta de Crédito") ||
                transaccion.getTipo().equals("Pago de Seguro")) {
            double montoPago = transaccion.getMonto();
            if (montoPago > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta corriente para realizar el pago.");
            }
            this.saldo -= montoPago;
            System.out.println("Se ha realizado un pago de $" + montoPago + " desde la cuenta corriente.");
        } else if (transaccion.getTipo().equals("Pago de Servicio Público")) {
            PagoServicioPublico pagoServicio = (PagoServicioPublico) transaccion;
            double monto = pagoServicio.getMonto();
            String tipoServicio = pagoServicio.getTipoServicioPublico();
            String numeroReferencia = pagoServicio.getNumeroReferencia();
            
            if (monto > this.saldo) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta corriente para pagar el servicio público.");
            }
            
            this.saldo -= monto;
            System.out.println("Se ha pagado $" + monto + " del servicio público '" + tipoServicio + "' con número de referencia '" + numeroReferencia + "' desde la cuenta corriente.");
        } else if (transaccion.getTipo().equals("Transferencia")) {
            Transferencia transferencia = (Transferencia) transaccion;
            double montoTransferencia = transferencia.getMonto();
            int numeroCuentaDestino = transferencia.getNumeroCuentaDestino();
            String idClienteDestino = transferencia.getIdClienteDestino();
            String nombreClienteDestino = transferencia.getNombreClienteDestino();
            
            if (montoTransferencia > this.saldo + sobregiroPermitido) {
                throw new IllegalArgumentException("Saldo insuficiente en la cuenta corriente para realizar la transferencia.");
            }
            
            this.saldo -= montoTransferencia;
            System.out.println("Se ha transferido $" + montoTransferencia + " a la cuenta con número " + numeroCuentaDestino + " del cliente " + nombreClienteDestino + " (ID: " + idClienteDestino + ")");
        } else {
            System.out.println("Tipo de transacción no válida para la cuenta corriente.");
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Número de cuenta: ").append(getNumeroCuenta()).append("\n");
        sb.append("Saldo: ").append(getSaldo()).append("\n");
        sb.append("Sobregiro permitido: ").append(sobregiroPermitido);
        return sb.toString();
    }
}
