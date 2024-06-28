package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.IGeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.Reporte;
import com.itextpdf.text.pdf.PdfException;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws PdfException {
        String filePath = "C:\\Nico\\utn\\3°Año\\Diseño";
        List<Reporte> reportes = new ArrayList<>();
        reportes.add(new Reporte("Reporte 1", "Este es el contenido del primer reporte."));
        reportes.add(new Reporte("Reporte 2", "Este es el contenido del segundo reporte."));
        reportes.add(new Reporte("Reporte 3", "Este es el contenido del tercer reporte."));

        IGeneradorPDF pdfGenerator = new PDFgenerator();
        pdfGenerator.generarPDF(reportes, filePath);
    }
}
