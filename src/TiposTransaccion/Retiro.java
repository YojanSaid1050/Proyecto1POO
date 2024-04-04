package TiposTransaccion;
import LogicaBanco.Transaccion;

public class Retiro extends Transaccion {
    public Retiro(double monto) {
        super("Retiro", monto);
    }
}
