package principal;

import java.util.Scanner;
import principal.factory.SolicitudFactory;
import principal.models.*;
import principal.storage.DataStore;
import principal.utils.ReportGenerator;

public class Principal {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--- MENÚ SISTEMA DE SOLICITUDES ---");
            System.out.println("1) Crear solicitud");
            System.out.println("2) Ver historial");
            System.out.println("3) Buscar por ID");
            System.out.println("4) Generar reporte (TXT + PDF) de una solicitud");
            System.out.println("5) Salir");
            System.out.print("Elija opción: ");

            int opcion = safeNextInt(entrada);
            switch (opcion) {
                case 1:
                    crearSolicitudInteractive(entrada);
                    break;
                case 2:
                    verHistorial();
                    break;
                case 3:
                    buscarPorId(entrada);
                    break;
                case 4:
                    generarReportes(entrada);
                    break;
                case 5:
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        entrada.close();
    }

    private static int safeNextInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Valor numérico requerido. Intente de nuevo: ");
            sc.next();
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    private static void crearSolicitudInteractive(Scanner sc) {
        System.out.print("Ingrese ID (número): ");
        int id = safeNextInt(sc);

        System.out.println("Tipo (1=COMISION,2=CAPACITACION,3=TRABAJO_CAMPO,4=OTRO): ");
        int t = safeNextInt(sc);
        TipoSolicitud tipo = TipoSolicitud.values()[Math.max(0, Math.min(t-1, TipoSolicitud.values().length-1))];

        System.out.print("Requiere hospedaje? (si/no): ");
        boolean reqHosp = sc.nextLine().trim().equalsIgnoreCase("si");

        System.out.print("Requiere boleto de avión? (si/no): ");
        boolean reqBoleto = sc.nextLine().trim().equalsIgnoreCase("si");

        boolean internacional = false;
        if (reqBoleto) {
            System.out.print("Es viaje internacional? (si/no): ");
            internacional = sc.nextLine().trim().equalsIgnoreCase("si");
        } else {
            System.out.print("Es viaje internacional? (si/no) (esto afecta tarifas): ");
            internacional = sc.nextLine().trim().equalsIgnoreCase("si");
        }

        System.out.print("Días de comisión: ");
        int dias = safeNextInt(sc);

        // Validaciones simples
        if (id <= 0 || dias <= 0) {
            System.out.println("ID y Días deben ser mayores que cero. Solicitud cancelada.");
            return;
        }

        Solicitud s = SolicitudFactory.crearSolicitud(id, tipo, reqHosp, reqBoleto, internacional, dias);
        DataStore.add(s);

        System.out.println("Solicitud creada:\n" + s);
    }

    private static void verHistorial() {
        System.out.println("--- HISTORIAL DE SOLICITUDES ---");
        for (Solicitud s : DataStore.getAll()) {
            System.out.println(s);
            System.out.println("-------------------------");
        }
    }

    private static void buscarPorId(Scanner sc) {
        System.out.print("Ingrese ID a buscar: ");
        int id = safeNextInt(sc);
        Solicitud s = DataStore.findById(id);
        if (s == null) System.out.println("No se encontró solicitud con ID " + id);
        else System.out.println(s);
    }

    private static void generarReportes(Scanner sc) {
        System.out.print("Ingrese ID para generar reporte: ");
        int id = safeNextInt(sc);
        Solicitud s = DataStore.findById(id);
        if (s == null) { System.out.println("No se encontró solicitud con ID " + id); return; }

        try {
            String rutaTxt = "solicitud_" + id + ".txt";
            String rutaPdf = "solicitud_" + id + ".pdf";
            ReportGenerator.generarTXT(s, rutaTxt);
            ReportGenerator.generarPDF(s, rutaPdf);
            System.out.println("Reportes generados: " + rutaTxt + " , " + rutaPdf);
        } catch (Exception e) {
            System.out.println("Error generando reportes: " + e.getMessage());
        }
    }
}