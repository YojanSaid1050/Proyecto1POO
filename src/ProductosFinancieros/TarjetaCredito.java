package ProductosFinancieros;

import Excepciones.BancoException;
import LogicaBanco.Cliente;
import LogicaBanco.ProductoFinanciero;

public class TarjetaCredito extends ProductoFinanciero {
    private double tasaInteres;
    private double deuda;

    public TarjetaCredito(Cliente cliente, double saldo, double tasaInteres) {
        super(cliente, saldo);
        this.tasaInteres = tasaInteres;
        this.deuda = saldo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
        actualizarDeuda();
    }

    public double getDeuda() {
        return deuda;
    }

    private void actualizarDeuda() {
        deuda = saldo + (saldo * tasaInteres);
    }

    @Override
    public void consignar(double cantidad) {
        saldo += cantidad;
        actualizarDeuda();
    }

    @Override
    public void retirar(double cantidad) throws BancoException {
        if (cantidad <= 0) {
            throw new BancoException("La cantidad a retirar debe ser mayor que cero.");
        }
        if (cantidad > saldo) {
            throw new BancoException("No se puede retirar una cantidad mayor que el saldo disponible.");
        }
        saldo -= cantidad;
    }
    
    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }
    
	@Override
    public String toString() {
        return "Tarjeta de Crédito\n" +
               "Saldo: " + saldo + "\n" +
               "Tasa de interés: " + tasaInteres + "\n" +
               "Deuda: " + deuda + "\n";
    }

	@Override
    public void transferirACuenta(ProductoFinanciero cuentaDestino, double cantidad) throws BancoException {
        if (cantidad <= 0) {
            throw new BancoException("La cantidad a transferir debe ser mayor que cero.");
        }
        if (saldo < cantidad) {
            throw new BancoException("No hay suficiente saldo en la tarjeta de crédito para realizar la transferencia.");
        }

        this.retirar(cantidad);
        cuentaDestino.consignar(cantidad);
    }
}
