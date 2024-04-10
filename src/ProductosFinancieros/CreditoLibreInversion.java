package ProductosFinancieros;

import Excepciones.BancoException;
import LogicaBanco.Cliente;
import LogicaBanco.ProductoFinanciero;

public class CreditoLibreInversion extends ProductoFinanciero {
    private double tasaInteres;
    private double deuda;

    public CreditoLibreInversion(Cliente cliente, double saldo, double tasaInteres) {
        super(cliente, saldo);
        this.tasaInteres = tasaInteres;
        this.deuda = saldo + (saldo * tasaInteres);
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public double getDeuda() {
        return deuda;
    }
    
    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    @Override
    public void consignar(double cantidad) throws BancoException {
        if (cantidad <= 0) {
            throw new BancoException("La cantidad a consignar debe ser mayor que cero.");
        }
        if (cantidad > deuda) {
            throw new BancoException("La cantidad a consignar no puede ser mayor que la deuda.");
        }
        deuda -= cantidad;
        if (deuda <= 0) {
            cliente.getProductosFinancieros().remove(this);
        }
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
    @Override
    public void transferirACuenta(ProductoFinanciero destino, double cantidad) throws BancoException {
        if (cantidad <= 0) {
            throw new BancoException("La cantidad a transferir debe ser mayor que cero.");
        }
        if (cantidad > this.saldo) {
            throw new BancoException("No hay suficiente saldo en el crédito de libre inversión para realizar la transferencia.");
        }

        destino.consignar(cantidad);
        this.saldo -= cantidad;
    }
    @Override
    public String toString() {
        return "Crédito de Libre Inversión\n" +
               "Saldo: " + saldo + "\n" +
               "Tasa de interés: " + tasaInteres + "\n" +
               "Deuda: " + deuda +"\n";
    }
}
