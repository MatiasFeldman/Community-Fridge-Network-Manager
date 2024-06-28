package ar.edu.utn.frba.dds.models.main;


import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.IGeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.IReporte;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteMock;
import com.itextpdf.text.pdf.PdfException;

import java.util.ArrayList;
import java.util.List;

public class MainReportes {
    public static void main(String[] args) throws PdfException {
        String path = "";
        List<IReporte> reportes = new ArrayList<>();
        reportes.add(new ReporteMock("Reporte 1", "Este es el contenido del primer reporte."));
        reportes.add(new ReporteMock("Reporte 2", "Este es el contenido del segundo reporte."));
        reportes.add(new ReporteMock("Reporte 3", "Este es el contenido del tercer reporte."));

        IGeneradorPDF pdfGenerator = new PDFgenerator();

        pdfGenerator.generarPDF(reportes, path);
    }
}
