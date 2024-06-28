package ar.edu.utn.frba.dds.models.entities.helpers.reportes;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PDFgenerator implements IGeneradorPDF {

    @Override
    public void generarPDF(List<Reporte> reportes, String path) {
        LocalDate date = LocalDate.now();

        String nombrePDF = path + "/reporte_semanal_" + date + ".pdf";

        // Crear y escribir en el documento PDF
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nombrePDF));
            document.open();
            for (int i = 0; i < reportes.size(); i++) {
                Reporte reporte = reportes.get(i);

                // Añadir contenido de cada reporte a una nueva página
                document.add(new Paragraph("Reporte Semanal"));
                document.add(new Paragraph("Fecha: " + date));
                document.add(new Paragraph("Contenido del reporte: " + reporte.getNombre()));
                document.add(new Paragraph(reporte.getContenido()));

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

