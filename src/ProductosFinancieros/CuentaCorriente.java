package ProductosFinancieros;
import LogicaBanco.ProductoFinanciero;
import LogicaBanco.Transaccion;
import TiposTransaccion.Consignacion;

public class CuentaCorriente extends ProductoFinanciero {
    private double sobregiroPermitido;

    public CuentaCorriente(int numeroCuenta, double saldoInicial, double sobregiroPermitido) {
        super(numeroCuenta, saldoInicial);
        this.sobregiroPermitido = sobregiroPermitido;
    }

    public double getSobregiroPermitido() {
        return sobregiroPermitido;
    }
    
    public void retirar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a retirar debe ser mayor que cero.");
            return;
        }
        if (monto > getSaldo()) {
            System.out.println("El monto a retirar excede el saldo disponible en la cuenta corriente.");
            return;
        }
        setSaldo(getSaldo() - monto);
        System.out.println("Se ha retirado $" + monto + " de la cuenta corriente.");
    }
    
    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Consignacion) {
            Consignacion consignacion = (Consignacion) transaccion;
            double montoConsignacion = consignacion.getMonto();
            double nuevoSaldo = getSaldo() + montoConsignacion;
            setSaldo(nuevoSaldo);
            System.out.println("Consignación de $" + montoConsignacion + " realizada en la cuenta corriente. Nuevo saldo: $" + nuevoSaldo);
        } else {
            System.out.println("Tipo de transacción no válido para la cuenta corriente.");
        }
    }

    @Override
    public String toString() {
        return "\n" +
        	   "Cuenta Corriente:\n" +
               "Número de cuenta: " + getNumeroCuenta() + "\n" +
               "Saldo: $" + getSaldo() + "\n" +
               "Sobregiro permitido: $" + sobregiroPermitido;
    }
}
