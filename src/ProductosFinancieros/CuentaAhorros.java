package ProductosFinancieros;

import Excepciones.BancoException;
import LogicaBanco.Cliente;
import LogicaBanco.ProductoFinanciero;

public class CuentaAhorros extends ProductoFinanciero {
    private double tasaInteres;

    public CuentaAhorros(Cliente cliente, double saldoInicial, double tasaInteres) {
        super(cliente, saldoInicial);
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public void consignar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Se ha consignado $" + cantidad + " a la cuenta Ahorros.");
        } else {
            System.out.println("La cantidad a consignar debe ser mayor que cero.");
        }
    }


    @Override
    public void retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            System.out.println("Se ha retirado $" + cantidad + " de la Cuenta Ahorros.");
        } else {
            System.out.println("No es posible retirar esa cantidad de la Cuenta Ahorros.");
        }
    }


    @Override
    public String toString() {
        return "Cuenta de Ahorros\n" +
               "Saldo: " + saldo + "\n" +
               "Tasa de interés: " + tasaInteres + "\n";
    }

	@Override
	public void transferirACuenta(ProductoFinanciero cuentaDestino, double cantidad) throws BancoException {
		// TODO Auto-generated method stub
		
	}
}
