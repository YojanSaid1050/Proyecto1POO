package ProductosFinancieros;

import Excepciones.BancoException;
import LogicaBanco.Cliente;
import LogicaBanco.ProductoFinanciero;

public class TarjetaDebito extends ProductoFinanciero {
    public TarjetaDebito(Cliente cliente, double saldoInicial) {
        super(cliente, saldoInicial);
    }

    @Override
    public void consignar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Se ha consignado $" + cantidad + " a la tarjeta de débito.");
        } else {
            System.out.println("La cantidad a consignar debe ser mayor que cero.");
        }
    }

    @Override
    public void retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            System.out.println("Se ha retirado $" + cantidad + " de la tarjeta de débito.");
        } else {
            System.out.println("No es posible retirar esa cantidad de la tarjeta de débito.");
        }
    }

    @Override
    public String toString() {
        return "Tarjeta de Débito\n" +
               "Saldo: $" + saldo + "\n";
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
