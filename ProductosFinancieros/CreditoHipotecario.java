
public class CreditoHipotecario extends ProductoFinanciero {
    private double montoPrestamo;
    private double tasaInteres;
    private double deuda;

    public CreditoHipotecario(int numeroCuenta, double saldoInicial, double montoPrestamo, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.montoPrestamo = montoPrestamo;
        this.tasaInteres = tasaInteres;
        this.deuda = montoPrestamo+(montoPrestamo*tasaInteres);
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
    
    // Método para realizar el pago de la deuda del crédito hipotecario
    public void realizarPagoCredito(double monto) throws BancoException {
        if (monto <= 0) {
            throw new BancoException("El monto del pago debe ser mayor que cero.");
        }
        if (monto <= deuda) {
            deuda -= monto; // Reducir la deuda en función del monto pagado
            System.out.println("Pago de $" + monto + " realizado. Deuda restante: $" + deuda);
        } else {
            throw new BancoException("El monto del pago excede la deuda actual.");
        }
    }
    
    // Método para establecer una nueva deuda
    public void setDeuda(double nuevaDeuda) {
        this.deuda = nuevaDeuda;
    }

    // Sobrescritura del método toString para representar la información del crédito hipotecario como una cadena de texto
    @Override
    public String toString() {
        return "\n" +
               "Crédito Hipotecario:\n" +
               "Monto del préstamo: $" + montoPrestamo + "\n" +
               "Tasa de interés: " + tasaInteres * 100 + "%\n" +
               "Deuda restante: $" + deuda;
    }

    // Implementación del método abstracto procesarTransaccion de la clase ProductoFinanciero
    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof PagoCreditoHipotecario) {
            PagoCreditoHipotecario pago = (PagoCreditoHipotecario) transaccion;
            double montoPago = pago.getMonto();
            try {
                realizarPagoCredito(montoPago);
            } catch (BancoException e) {
                System.out.println("Error al procesar el pago del crédito hipotecario: " + e.getMessage());
            }
        } else {
            System.out.println("Tipo de transacción no válida para el crédito hipotecario.");
        }
    }
}
