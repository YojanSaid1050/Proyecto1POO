public class Transferencia extends Transaccion {
    private int numeroCuentaDestino;
    private String idClienteDestino;
    private String nombreClienteDestino;

    public Transferencia(double monto, int numeroCuentaDestino, String idClienteDestino, String nombreClienteDestino) {
        super("Transferencia", monto);
        this.numeroCuentaDestino = numeroCuentaDestino;
        this.idClienteDestino = idClienteDestino;
        this.nombreClienteDestino = nombreClienteDestino;
    }

    public int getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    public String getIdClienteDestino() {
        return idClienteDestino;
    }

    public String getNombreClienteDestino() {
        return nombreClienteDestino;
    }
}
