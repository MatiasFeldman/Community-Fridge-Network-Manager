package ar.edu.utn.frba.dds.models.entities.helpers.reportes;

import ar.edu.utn.frba.dds.models.entities.reportes.Reporte;
import com.itextpdf.text.pdf.PdfException;

import java.util.List;

public interface GeneradorPDF {
    public void generarPDF(List<Reporte> reportes, String path) throws PdfException;
}
