class Transferencia extends Transaccion {
    private int cuentaDestino;

    public Transferencia(int cuentaDestino, double monto) {
        super("Transferencia", monto);
        this.cuentaDestino = cuentaDestino;
    }

    public int getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(int cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
}