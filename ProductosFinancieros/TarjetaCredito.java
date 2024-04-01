public class TarjetaCredito extends ProductoFinanciero {
    private double limiteCredito;
    private double tasaInteres;
    private double montoRetirado;
    private int cuotasTotales;
    private int cuotasPagadas;

    public TarjetaCredito(int numeroCuenta, double saldoInicial, double limiteCredito, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.limiteCredito = limiteCredito;
        this.tasaInteres = tasaInteres;
        this.montoRetirado = 0;
        this.cuotasTotales = 0;
        this.cuotasPagadas = 0;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public double getMontoRetirado() {
        return montoRetirado;
    }

    public int getCuotasTotales() {
        return cuotasTotales;
    }

    public int getCuotasPagadas() {
        return cuotasPagadas;
    }

    public void solicitarCredito(double monto, int cuotas) {
        if (cuotas <= 0) {
            throw new IllegalArgumentException("La cantidad de cuotas debe ser mayor que cero.");
        }
        double montoMensual = monto / cuotas;
        if (montoMensual > this.limiteCredito) {
            throw new IllegalArgumentException("El monto mensual excede el límite de crédito.");
        }
        this.saldo += monto;
        this.montoRetirado = monto;
        this.cuotasTotales = cuotas;
        this.cuotasPagadas = 0;
        System.out.println("Se ha solicitado un crédito de $" + monto + " a " + cuotas + " cuotas.");
    }

    public void pagarCuota(double monto) {
        if (monto > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el pago.");
        }
        if (monto > this.montoRetirado) {
            throw new IllegalArgumentException("No se puede pagar más del monto retirado.");
        }
        this.saldo -= monto;
        this.montoRetirado -= monto;
        this.cuotasPagadas++;
        System.out.println("Se ha pagado una cuota de $" + monto + ". Cuotas pagadas: " + cuotasPagadas + ". Cuotas restantes: " + (cuotasTotales - cuotasPagadas));
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion.getTipo().equals("Pago de Tarjeta de Crédito")) {
            double montoPago = transaccion.getMonto() * (1 + tasaInteres);
            if (montoPago > montoRetirado) {
                throw new IllegalArgumentException("No se puede pagar más del monto retirado.");
            }
            this.saldo -= montoPago;
            this.montoRetirado -= montoPago;
            System.out.println("Pago de $" + montoPago + " realizado con éxito para la tarjeta de crédito.");
        } else {
            System.out.println("Tipo de transacción no válida para la tarjeta de crédito.");
        }
    }

    @Override
    public String toString() {
        return "Tarjeta de Crédito\n" +
               "Saldo: " + getSaldo() + "\n" +
               "Límite de crédito: " + limiteCredito + "\n" +
               "Tasa de interés: " + tasaInteres + "\n" +
               "Monto retirado: " + montoRetirado + "\n" +
               "Cuotas totales: " + cuotasTotales + "\n" +
               "Cuotas pagadas: " + cuotasPagadas;
    }
}
