package TiposTransaccion;
import LogicaBanco.Transaccion;

public class PagoTarjetaCredito extends Transaccion {
    public PagoTarjetaCredito(double monto) {
        super("Pago de Tarjeta de Crédito", monto);
    }
}
