package TiposTransaccion;
import LogicaBanco.Transaccion;

public class Consignacion extends Transaccion {
    public Consignacion(double monto) {
        super("Consignacion", monto);
    }
}
