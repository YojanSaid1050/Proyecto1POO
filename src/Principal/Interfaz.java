package Principal;

import LogicaBanco.*;
import Persistencia.ArchivoTextoPlano;

import java.util.Scanner;

import Excepciones.BancoException;
import java.io.IOException;

public class Interfaz {
    private Banco banco;
    private LogicaInterfaz logicaInterfaz;
    private static final String NOMBRE_ARCHIVO = "informacion_banco.csv";

    public Interfaz(Banco banco) {
        this.banco = banco;
        this.logicaInterfaz = new LogicaInterfaz(banco);
        new ArchivoTextoPlano();
    }

    public void mostrarMenu() throws BancoException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Crear nuevo cliente");
                System.out.println("2. Añadir Producto Financiero");
                System.out.println("3. Mostrar clientes y sus productos financieros");
                System.out.println("4. Consignar");
                System.out.println("5. Retirar");
                System.out.println("6. Transferir entre clientes");
                System.out.println("7. Transferir entre cuentas del mismo cliente");
                System.out.println("8. Mostrar Transacciones del cliente");
                System.out.println("9. Guardar información del clientes en archivo");
                System.out.println("10. Cargar información del clientes desde archivo");
                System.out.println("0. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        logicaInterfaz.crearCliente(scanner);
                        break;
                    case 2:
                        logicaInterfaz.agregarProductoFinanciero(scanner);
                        break;
                    case 3:
                        logicaInterfaz.mostrarClientesProductosFinancieros();
                        break;
                    case 4:
                        logicaInterfaz.consignar(scanner);
                        break;
                    case 5:
                        logicaInterfaz.retirar(scanner);
                        break;
                    case 6:
                        logicaInterfaz.transferirSaldoEntreClientes(scanner);
                        break;
                    case 7:
                    	logicaInterfaz.transferirSaldoEntreCuentas(scanner);
                    	break;
                    case 8:
                    	logicaInterfaz.mostrarTransaccionesCliente(scanner);
                    	break;
                    case 9:
                        ArchivoTextoPlano.guardarClientesCSV(NOMBRE_ARCHIVO, banco.getClientes());
                        System.out.println("Información del banco guardada exitosamente en el archivo " + NOMBRE_ARCHIVO);
                        break;
                    case 10:
                        banco.getClientes().addAll(ArchivoTextoPlano.cargarClientesCSV(NOMBRE_ARCHIVO));
                        System.out.println("Información del banco cargada exitosamente desde el archivo " + NOMBRE_ARCHIVO);
                        break;
                    case 11:
                        try {
                            ArchivoTextoPlano.guardarProductosFinancierosClientesCSV("productos_financieros_clientes.csv", banco.getClientes());
                        } catch (IOException e) {
                        }
                        break;
                    
                    case 0:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        }
    }


    public static void main(String[] args) throws BancoException {
        Banco banco = new Banco();
        Interfaz interfaz = new Interfaz(banco);

        interfaz.mostrarMenu();
    }
}
