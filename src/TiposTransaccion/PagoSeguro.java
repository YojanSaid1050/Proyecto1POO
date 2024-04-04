package TiposTransaccion;
import LogicaBanco.Transaccion;

public class PagoSeguro extends Transaccion {
    public PagoSeguro(double monto) {
        super("Pago de Seguro", monto);
    }
}
