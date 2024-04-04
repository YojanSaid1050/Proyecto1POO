package ProductosFinancieros;

import LogicaBanco.ProductoFinanciero;
import LogicaBanco.Transaccion;

public class TarjetaCredito extends ProductoFinanciero {
    private double limiteCredito;
    private double tasaInteres;
    private boolean montoRetirado;

    public TarjetaCredito(int numeroCuenta, double saldoInicial, double limiteCredito, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.limiteCredito = limiteCredito;
        this.tasaInteres = tasaInteres;
        this.montoRetirado = false;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public boolean getMontoRetirado() {
        return montoRetirado;
    }

    public void setMontoRetirado(boolean montoRetirado) {
        this.montoRetirado = montoRetirado;
    }

    public void realizarPago(double monto) {
        if (montoRetirado) {
            double nuevoSaldo = getSaldo() - monto;
            if (nuevoSaldo >= 0) {
                setSaldo(nuevoSaldo);
                montoRetirado = false; // Actualizar el estado del montoRetirado
                System.out.println("Pago realizado con éxito. Nuevo saldo: $" + getSaldo());
            } else {
                System.out.println("No se puede realizar el pago. El monto excede el saldo disponible.");
            }
        } else {
            System.out.println("No se puede realizar el pago. No se ha retirado ningún monto.");
        }
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        // Implementación específica de procesamiento de transacción para la tarjeta de crédito
        // Aquí puedes agregar lógica para procesar transacciones relacionadas con la tarjeta de crédito
    }

    @Override
    public String toString() {
        return "\n" +
               "Tarjeta de Crédito\n" +
               "Límite de crédito: $" + limiteCredito + "\n" +
               "Tasa de interés: " + tasaInteres * 100 + "%\n" +
               "Saldo: $" + getSaldo();
    }
}
