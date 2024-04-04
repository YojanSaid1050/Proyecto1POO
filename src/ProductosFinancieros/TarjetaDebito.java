package ProductosFinancieros;
import LogicaBanco.ProductoFinanciero;
import LogicaBanco.Transaccion;
import TiposTransaccion.Consignacion;
import TiposTransaccion.PagoCreditoHipotecario;

public class TarjetaDebito extends ProductoFinanciero {

    // Constructor
    public TarjetaDebito(int numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    // Método para retirar un monto del saldo de la tarjeta de débito
    public void retirar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a retirar debe ser mayor que cero.");
            return;
        }
        if (monto > getSaldo()) {
            System.out.println("El monto a retirar excede el saldo disponible en la tarjeta de débito.");
            return;
        }
        setSaldo(getSaldo() - monto);
        System.out.println("Se ha retirado $" + monto + " de la tarjeta de débito. Nuevo saldo: $" + getSaldo());
    }

    // Sobrescritura del método procesarTransaccion para manejar transacciones específicas de la tarjeta de débito
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

    // Sobrescritura del método toString para representar la información de la tarjeta de débito como una cadena de texto
    @Override
    public String toString() {
        return "\n" +
               "Tarjeta de Débito:\n" +
               "Saldo: $" + getSaldo();
    }
}
