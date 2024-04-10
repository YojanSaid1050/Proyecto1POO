package LogicaBanco;

import Excepciones.BancoException;

public abstract class ProductoFinanciero {
    protected Cliente cliente;
    protected double saldo;

    public ProductoFinanciero(Cliente cliente, double saldoInicial) {
        this.cliente = cliente;
        this.saldo = saldoInicial;
        cliente.agregarProducto(this);
    }

    public double consultarSaldo() {
        return saldo;
    }

    public abstract void consignar(double cantidad) throws BancoException;

    public abstract void retirar(double cantidad) throws BancoException;

    public void transferir(ProductoFinanciero destino, double cantidad) throws BancoException {
        if (cantidad <= 0) {
            throw new BancoException("La cantidad a transferir debe ser mayor que cero.");
        }
        if (this.saldo < cantidad) {
            throw new BancoException("No hay suficiente saldo para realizar la transferencia.");
        }

        this.retirar(cantidad);
        destino.consignar(cantidad);
    }

    public abstract void transferirACuenta(ProductoFinanciero cuentaDestino, double cantidad) throws BancoException;

    @Override
    public abstract String toString();
}
