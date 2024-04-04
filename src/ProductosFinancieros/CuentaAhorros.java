package ProductosFinancieros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import LogicaBanco.ProductoFinanciero;
import LogicaBanco.Transaccion;
import TiposTransaccion.Consignacion;
import TiposTransaccion.Retiro;

public class CuentaAhorros extends ProductoFinanciero {
    private double tasaInteres;
    private List<Transaccion> transacciones;

    public CuentaAhorros(int numeroCuenta, double saldoInicial, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.tasaInteres = tasaInteres;
        this.transacciones = new ArrayList<>();
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public List<Transaccion> getTransacciones() {
        return Collections.unmodifiableList(transacciones);
    }

    public void agregarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }
    
    public void retirar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a retirar debe ser mayor que cero.");
            return;
        }
        if (monto > getSaldo()) {
            System.out.println("El monto a retirar excede el saldo disponible en la cuenta de ahorros.");
            return;
        }
        setSaldo(getSaldo() - monto);
        System.out.println("Se ha retirado $" + monto + " de la cuenta de ahorros.");
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        if (transaccion instanceof Consignacion) {
            Consignacion consignacion = (Consignacion) transaccion;
            double montoConsignado = consignacion.getMonto();
            this.saldo += montoConsignado;
            agregarTransaccion(transaccion);
            System.out.println("Se ha consignado $" + montoConsignado + " en la cuenta de ahorros. Nuevo saldo: $" + this.saldo);
        } else if (transaccion instanceof Retiro) {
            Retiro retiro = (Retiro) transaccion;
            double montoRetirado = retiro.getMonto();
            if (montoRetirado <= this.saldo) {
                this.saldo -= montoRetirado;
                agregarTransaccion(transaccion);
                System.out.println("Se ha retirado $" + montoRetirado + " de la cuenta de ahorros. Nuevo saldo: $" + this.saldo);
            } else {
                System.out.println("Error: Saldo insuficiente para realizar el retiro.");
            }
        } else {
            System.out.println("Tipo de transacción no válida para la cuenta de ahorros.");
        }
    }

    @Override
    public String toString() {
        return "\n" +
               "Cuenta de Ahorros:\n" +
               "Saldo: $" + getSaldo() + "\n" +
               "Tasa de interés: " + (tasaInteres * 100) + "%";
    }
}
