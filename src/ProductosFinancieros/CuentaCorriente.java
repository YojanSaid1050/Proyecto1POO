package ProductosFinancieros;

import Excepciones.BancoException;
import LogicaBanco.Cliente;
import LogicaBanco.ProductoFinanciero;

public class CuentaCorriente extends ProductoFinanciero {
    private double sobregiroPermitido;

    public CuentaCorriente(Cliente cliente, double saldoInicial, double sobregiro) {
        super(cliente, saldoInicial);
        this.sobregiroPermitido = sobregiro;
    }
    public double getSobregiroPermitido() {
        return sobregiroPermitido;
    }

    @Override
    public void consignar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Se ha consignado $" + cantidad + " a la cuenta corriente.");
        } else {
            System.out.println("La cantidad a consignar debe ser mayor que cero.");
        }
    }


    @Override
    public void retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            System.out.println("Se ha retirado $" + cantidad + " de la Cuenta Corriente.");
        } else {
            System.out.println("No es posible retirar esa cantidad de la Cuenta Corriente.");
        }
    }


    @Override
    public String toString() {
        return "Cuenta Corriente\n" +
               "Saldo: " + saldo + "\n" +
               "Sobregiro permitido: " + sobregiroPermitido + "\n";
    }

	@Override
	public void transferirACuenta(ProductoFinanciero cuentaDestino, double cantidad) throws BancoException {
		// TODO Auto-generated method stub
		
	}
}
