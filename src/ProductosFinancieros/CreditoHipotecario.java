package ProductosFinancieros;

import Excepciones.BancoException;
import LogicaBanco.Cliente;
import LogicaBanco.ProductoFinanciero;

public class CreditoHipotecario extends ProductoFinanciero {
    private double tasaInteres;
    private double deuda;

    public CreditoHipotecario(Cliente cliente, double saldo, double tasaInteres) {
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
  
    @Override
    public void consignar(double cantidad) throws BancoException {
        if (cantidad <= 0) {
            throw new BancoException("La cantidad a consignar debe ser mayor que cero.");
        }
        deuda -= cantidad;
        if (deuda <= 0) {
            cliente.getProductosFinancieros().remove(this);
        }
    }
    public void setDeuda(double deuda) {
        this.deuda = deuda;
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
    public String toString() {
        return "Crédito Hipotecario\n" +
               "Saldo: " + saldo + "\n" +
               "Tasa de interés: " + tasaInteres + "\n"+
               "Deuda: " + deuda + "\n";
    }
    
    @Override
    public void transferirACuenta(ProductoFinanciero destino, double cantidad) throws BancoException {
        if (cantidad <= 0) {
            throw new BancoException("La cantidad a transferir debe ser mayor que cero.");
        }
        if (cantidad > this.saldo) {
            throw new BancoException("No hay suficiente saldo en el crédito hipotecario para realizar la transferencia.");
        }

        // Realizar la transferencia al producto financiero destino
        destino.consignar(cantidad);
        // Actualizar el saldo del crédito hipotecario
        this.saldo -= cantidad;
    }

}
