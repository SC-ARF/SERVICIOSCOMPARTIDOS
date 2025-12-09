
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