package ProductosFinancieros;
import Excepciones.BancoException;
import LogicaBanco.ProductoFinanciero;
import LogicaBanco.Transaccion;
import TiposTransaccion.PagoCreditoLibreInversion;

public class CreditoLibreInversion extends ProductoFinanciero {
    private double montoPrestamo;
    private double tasaInteres;
    private double deuda;

    public CreditoLibreInversion(int numeroCuenta, double saldoInicial, double montoPrestamo, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.montoPrestamo = montoPrestamo;
        this.tasaInteres = tasaInteres;
        this.deuda = montoPrestamo;
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

    public void realizarPagoCredito(double montoPago) throws BancoException {
        if (montoPago <= 0) {
            throw new BancoException("El monto del pago debe ser mayor que cero.");
        }
        if (montoPago <= deuda) {
            deuda -= montoPago;
            System.out.println("Pago de $" + montoPago + " realizado. Deuda restante: $" + deuda);
        } else {
            throw new BancoException("El monto del pago excede la deuda actual.");
        }
    }

    @Override
    public String toString() {
        return "\n" +
               "Crédito de Libre Inversión:\n" +
               "Monto del préstamo: $" + montoPrestamo + "\n" +
               "Tasa de interés: " + tasaInteres * 100 + "%\n" +
               "Deuda restante: $" + deuda;
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof PagoCreditoLibreInversion) {
            PagoCreditoLibreInversion pago = (PagoCreditoLibreInversion) transaccion;
            double montoPago = pago.getMonto();
            try {
                realizarPagoCredito(montoPago);
            } catch (BancoException e) {
                System.out.println("Error al procesar el pago del crédito de libre inversión: " + e.getMessage());
            }
        } else {
            System.out.println("Tipo de transacción no válida para el crédito de libre inversión.");
        }
    }
}
