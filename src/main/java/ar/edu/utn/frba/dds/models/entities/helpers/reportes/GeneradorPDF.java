package ar.edu.utn.frba.dds.models.entities.helpers.reportes;
import com.itextpdf.text.pdf.PdfException;

import java.util.List;

public class GeneradorPDF {
    private IGeneradorPDF pdfGenerator;

    public GeneradorPDF(IGeneradorPDF pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    public void generarPDF(List<Reporte> reportes, String path) throws PdfException {
        pdfGenerator.generarPDF(reportes, path);
    }
}
