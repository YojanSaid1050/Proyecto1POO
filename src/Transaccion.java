public class Transaccion {
    private String tipo;
    private double monto;
    private String tipoServicioPublico;
    private String numeroReferencia;

    public Transaccion(String tipo, double monto) {
        this.tipo = tipo;
        this.monto = monto;
    }

    public Transaccion(String tipo, double monto, String tipoServicioPublico, String numeroReferencia) {
        this.tipo = tipo;
        this.monto = monto;
        this.tipoServicioPublico = tipoServicioPublico;
        this.numeroReferencia = numeroReferencia;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipoServicioPublico() {
        return tipoServicioPublico;
    }

    public String getNumeroReferencia() {
        return numeroReferencia;
    }
}
