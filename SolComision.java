# Proyecto: SistemaSolicitudesComision (Maven)

---

## Estructura del proyecto

```
SistemaSolicitudesComision/
├── pom.xml
└── src/main/java/principal/
    ├── Principal.java
    ├── models/
    │   ├── Solicitud.java
    │   ├── SolicitudNacional.java
    │   ├── SolicitudInternacional.java
    │   ├── Hospedaje.java
    │   ├── BoletoAvion.java
    │   ├── Viaticos.java
    │   └── TipoSolicitud.java
    ├── factory/
    │   └── SolicitudFactory.java
    ├── storage/
    │   └── DataStore.java
    └── utils/
        └── ReportGenerator.java
```

---

## pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>SistemaSolicitudesComision</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- OpenPDF para generar PDF (similar a iText) -->
        <dependency>
            <groupId>com.github.librepdf</groupId>
            <artifactId>openpdf</artifactId>
            <version>1.3.30</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>principal.Principal</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## src/main/java/principal/models/TipoSolicitud.java

```java
package principal.models;

public enum TipoSolicitud {
    COMISION,
    CAPACITACION,
    TRABAJO_CAMPO,
    OTRO
}
```

---

## src/main/java/principal/models/Hospedaje.java

```java
package principal.models;

public class Hospedaje {
    private boolean requiere;
    private int dias;
    private double tarifaPorDia; // tarifa base

    public Hospedaje(boolean requiere, int dias, double tarifaPorDia) {
        this.requiere = requiere;
        this.dias = dias;
        this.tarifaPorDia = tarifaPorDia;
    }

    public double calcularCosto() {
        if (!requiere) return 0.0;
        return dias * tarifaPorDia;
    }

    public boolean isRequiere() { return requiere; }
    public int getDias() { return dias; }

    @Override
    public String toString() {
        return "Hospedaje: requiere=" + requiere + ", dias=" + dias + ", costo=$" + String.format("%.2f", calcularCosto());
    }
}
```

---

## src/main/java/principal/models/BoletoAvion.java

```java
package principal.models;

public class BoletoAvion {
    private boolean requiere;
    private boolean internacional;
    private double tarifa; // tarifa calculada

    public BoletoAvion(boolean requiere, boolean internacional, double tarifa) {
        this.requiere = requiere;
        this.internacional = internacional;
        this.tarifa = tarifa;
    }

    public double calcularCosto() {
        if (!requiere) return 0.0;
        return tarifa;
    }

    @Override
    public String toString() {
        return "BoletoAvion: requiere=" + requiere + ", internacional=" + internacional + ", costo=$" + String.format("%.2f", calcularCosto());
    }
}
```

---

## src/main/java/principal/models/Viaticos.java

```java
package principal.models;

public class Viaticos {
    private int dias;
    private double montoDiario;

    public Viaticos(int dias, double montoDiario) {
        this.dias = dias;
        this.montoDiario = montoDiario;
    }

    public double calcularCosto() {
        return dias * montoDiario;
    }

    @Override
    public String toString() {
        return "Viáticos: dias=" + dias + ", monto diario=$" + String.format("%.2f", montoDiario) + ", total=$" + String.format("%.2f", calcularCosto());
    }
}
```

---

## src/main/java/principal/models/Solicitud.java

```java
package principal.models;

public abstract class Solicitud {
    protected int id;
    protected TipoSolicitud tipo;
    protected Hospedaje hospedaje;
    protected BoletoAvion boleto;
    protected Viaticos viaticos;
    protected int dias;

    public Solicitud(int id, TipoSolicitud tipo, Hospedaje hospedaje, BoletoAvion boleto, Viaticos viaticos, int dias) {
        this.id = id;
        this.tipo = tipo;
        this.hospedaje = hospedaje;
        this.boleto = boleto;
        this.viaticos = viaticos;
        this.dias = dias;
    }

    public abstract double calcularTotal();

    public int getId() { return id; }

    @Override
    public String toString() {
        return "Solicitud ID=" + id + "\nTipo=" + tipo + "\nDías=" + dias + "\n" + hospedaje + "\n" + boleto + "\n" + viaticos + "\nTOTAL=$" + String.format("%.2f", calcularTotal());
    }
}
```

---

## src/main/java/principal/models/SolicitudNacional.java

```java
package principal.models;

public class SolicitudNacional extends Solicitud {

    private static final double IMPUESTO = 0.16; // ejemplo: IVA sobre algunos rubros

    public SolicitudNacional(int id, TipoSolicitud tipo, Hospedaje hospedaje, BoletoAvion boleto, Viaticos viaticos, int dias) {
        super(id, tipo, hospedaje, boleto, viaticos, dias);
    }

    @Override
    public double calcularTotal() {
        double subtotal = hospedaje.calcularCosto() + boleto.calcularCosto() + viaticos.calcularCosto();
        double impuesto = subtotal * IMPUESTO;
        return subtotal + impuesto;
    }
}
```

---

## src/main/java/principal/models/SolicitudInternacional.java

```java
package principal.models;

public class SolicitudInternacional extends Solicitud {

    private static final double TASA_CAMBIO = 18.5; // ejemplo: para convertir USD a MXN (si aplica)
    private static final double RECARGO_INTERNACIONAL = 0.12; // recargo por trámites

    public SolicitudInternacional(int id, TipoSolicitud tipo, Hospedaje hospedaje, BoletoAvion boleto, Viaticos viaticos, int dias) {
        super(id, tipo, hospedaje, boleto, viaticos, dias);
    }

    @Override
    public double calcularTotal() {
        double subtotal = hospedaje.calcularCosto() + boleto.calcularCosto() + viaticos.calcularCosto();
        double recargo = subtotal * RECARGO_INTERNACIONAL;
        return subtotal + recargo; // supongamos que ya está en moneda local
    }
}
```

---

## src/main/java/principal/factory/SolicitudFactory.java

```java
package principal.factory;

import principal.models.*;

public class SolicitudFactory {

    // Tarifas base (pueden venir de config)
    private static final double TARIFA_HOSPEDAJE_NACIONAL = 900.0;
    private static final double TARIFA_HOSPEDAJE_INTERNACIONAL = 1800.0; // por noche

    private static final double BOLETO_NACIONAL_PROM = 3200.0;
    private static final double BOLETO_INTERNACIONAL_PROM = 15000.0;

    private static final double VIATICO_DIARIO_NAC = 500.0;
    private static final double VIATICO_DIARIO_INTL = 1200.0;

    public static Solicitud crearSolicitud(int id, TipoSolicitud tipo, boolean requiereHospedaje, boolean requiereBoleto,
                                           boolean internacional, int dias) {

        double tarifaHospedaje = internacional ? TARIFA_HOSPEDAJE_INTERNACIONAL : TARIFA_HOSPEDAJE_NACIONAL;
        Hospedaje h = new Hospedaje(requiereHospedaje, dias, tarifaHospedaje);

        double tarifaBoleto = 0.0;
        if (requiereBoleto) tarifaBoleto = internacional ? BOLETO_INTERNACIONAL_PROM : BOLETO_NACIONAL_PROM;
        BoletoAvion b = new BoletoAvion(requiereBoleto, internacional, tarifaBoleto);

        double montoViatico = internacional ? VIATICO_DIARIO_INTL : VIATICO_DIARIO_NAC;
        Viaticos v = new Viaticos(dias, montoViatico);

        if (internacional) {
            return new SolicitudInternacional(id, tipo, h, b, v, dias);
        } else {
            return new SolicitudNacional(id, tipo, h, b, v, dias);
        }
    }
}
```

---

## src/main/java/principal/storage/DataStore.java

```java
package principal.storage;

import principal.models.Solicitud;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final List<Solicitud> solicitudes = new ArrayList<>();

    public static void add(Solicitud s) { solicitudes.add(s); }
    public static List<Solicitud> getAll() { return new ArrayList<>(solicitudes); }
    public static Solicitud findById(int id) {
        for (Solicitud s : solicitudes) if (s.getId() == id) return s;
        return null;
    }
}
```

---

## src/main/java/principal/utils/ReportGenerator.java

```java
package principal.utils;

import principal.models.Solicitud;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class ReportGenerator {

    public static void generarTXT(Solicitud s, String ruta) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter(ruta))) {
            out.println(s.toString());
        }
    }

    public static void generarPDF(Solicitud s, String ruta) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(ruta));
        doc.open();
        String[] lineas = s.toString().split("\\n");
        for (String l : lineas) {
            doc.add(new Paragraph(l));
        }
        doc.close();
    }
}
```

---

## src/main/java/principal/Principal.java

```java
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
```

---

## Instrucciones de uso

1. Copia la estructura en un proyecto Maven con el `pom.xml` indicado.
2. Ejecuta `mvn compile exec:java -Dexec.mainClass=principal.Principal` o configura tu IDE y ejecuta la clase `principal.Principal`.
3. El menú permite crear solicitudes, ver historial, buscar por ID y generar reportes (TXT y PDF).

---

## Notas

- La generación de PDF usa la librería OpenPDF (declarada en `pom.xml`). Si prefieres iText, cámbiala en el `pom.xml` y en `ReportGenerator`.
- Las tarifas (hotel, viáticos, boletos) están en `SolicitudFactory` y puedes leerlas de un archivo de configuración en una mejora futura.
- El proyecto está preparado para extensiones: serializar a base de datos, añadir GUI, etc.

---

Si quieres que haga alguna de estas mejoras ahora (leer tarifas desde JSON, exportar historial, agregar validaciones por rubro, o un instalador), dime cuál y lo agrego.
