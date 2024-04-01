public class PagoServicioPublico extends Transaccion {
    private String tipoServicioPublico;
    private String numeroReferencia;

    public PagoServicioPublico(double monto, String tipoServicioPublico, String numeroReferencia) {
        super("Pago de Servicio Público", monto);
        this.tipoServicioPublico = tipoServicioPublico;
        this.numeroReferencia = numeroReferencia;
    }

    public String getTipoServicioPublico() {
        return tipoServicioPublico;
    }

    public String getNumeroReferencia() {
        return numeroReferencia;
    }
}
