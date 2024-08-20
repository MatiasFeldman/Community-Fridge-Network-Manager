package ar.edu.utn.frba.dds.models.entities.helpers.reportes;
import ar.edu.utn.frba.dds.models.entities.reportes.Reporte;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PDFgenerator implements GeneradorPDF {

    @Override
    public void generarPDF(List<Reporte> reportes, String path) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timeStamp = dateTime.format(dateFormat);

        String nombrePDF = path + "/reporte_semanal_" + timeStamp + ".pdf";

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nombrePDF));
            document.open();
            for (int i = 0; i < reportes.size(); i++) {
                Reporte reporte = reportes.get(i);

                // Añadir contenido de cada reporte a una nueva página
                document.add(new Paragraph("Reporte Semanal" + " - " + reporte.nombre()));
                document.add(new Paragraph("Fecha: " + timeStamp));
                document.add(new Paragraph(reporte.contenido()));

                if (i < reportes.size() - 1) {
                    document.newPage();
                }
            }

            document.close();
            System.out.println("PDF generado: " + nombrePDF);
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

