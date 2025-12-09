package Principal;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("SOLICITUD DE COMISIÓN");
        System.out.println("=====================\n");

        System.out.print("Número de Identificador: ");
        int identificador = entrada.nextInt();
        entrada.nextLine();

        System.out.println("\nTipo de Solicitud (1=COMISION, 2=REPOSICION, 3=LIQUIDACION): ");
        int opcion = entrada.nextInt();
        TipoSolicitud tipo = TipoSolicitud.values()[opcion - 1];
        entrada.nextLine();

        System.out.print("¿Requiere hospedaje? (si/no): ");
        boolean requiereHospedaje = entrada.nextLine().equalsIgnoreCase("si");

        System.out.print("¿Requiere boleto de avión? (si/no): ");
        boolean requiereBoleto = entrada.nextLine().equalsIgnoreCase("si");

        System.out.print("Días de Comisión: ");
        int dias = entrada.nextInt();

        Hospedaje h = new Hospedaje(requiereHospedaje, dias);
        BoletoAvion b = new BoletoAvion(requiereBoleto);

        Solicitud s = new Solicitud(identificador, tipo, h, b, dias);

        System.out.println("\n" + s);

        entrada.close();
    }
}
