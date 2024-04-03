import java.util.ArrayList;
import java.util.List;

public class Seguro extends ProductoFinanciero {
    private String tipoSeguro;
    private double primaSeguro;
    private List<String> coberturas;

    public Seguro(int numeroCuenta, double saldoInicial, String tipoSeguro, double primaSeguro) {
        super(numeroCuenta, saldoInicial);
        this.tipoSeguro = tipoSeguro;
        this.primaSeguro = primaSeguro;
        this.coberturas = new ArrayList<>();
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public double getPrimaSeguro() {
        return primaSeguro;
    }

    public List<String> getCoberturas() {
        return coberturas;
    }

    public void agregarCobertura(String cobertura) {
        coberturas.add(cobertura);
    }

    public void quitarCobertura(String cobertura) {
        coberturas.remove(cobertura);
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        // Implementar lógica específica para transacciones de seguro
        System.out.println("Las transacciones de seguro se procesarán aquí en el futuro.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        sb.append("Seguro: ").append(tipoSeguro).append("\n");
        sb.append("Prima del seguro: ").append(primaSeguro).append("\n");
        sb.append("Coberturas:\n");
        for (String cobertura : coberturas) {
            sb.append("- ").append(cobertura).append("\n");
        }
        sb.append("Saldo: ").append(getSaldo());
        return sb.toString();
    }

}
