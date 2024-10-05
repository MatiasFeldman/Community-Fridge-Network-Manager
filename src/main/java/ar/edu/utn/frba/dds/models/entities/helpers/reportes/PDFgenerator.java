package ar.edu.utn.frba.dds.models.entities.helpers.reportes;
import ar.edu.utn.frba.dds.models.entities.reportes.Reporte;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PDFgenerator implements GeneradorPDF {

    @Override
    public void guardarPdfEnPath(List<Reporte> reportes, String path) {
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

            System.out.println("PDF generado: " + nombrePDF);
            document.close();

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ByteArrayOutputStream generarPdfParaEnviar(List<Reporte> reportes) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timeStamp = dateTime.format(dateFormat);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            for (int i = 0; i < reportes.size(); i++) {
                Reporte reporte = reportes.get(i);
                document.add(new Paragraph("Reporte Semanal - " + reporte.nombre()));
                document.add(new Paragraph("Fecha: " + timeStamp));
                document.add(new Paragraph(reporte.contenido()));

                if (i < reportes.size() - 1) {
                    document.newPage();
                }
            }

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }

        return byteArrayOutputStream;
    }

}

