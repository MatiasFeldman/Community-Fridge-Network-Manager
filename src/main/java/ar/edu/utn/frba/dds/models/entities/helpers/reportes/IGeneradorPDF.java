package ar.edu.utn.frba.dds.models.entities.helpers.reportes;

import com.itextpdf.text.pdf.PdfException;

import java.util.List;

public interface IGeneradorPDF {
    public void generarPDF(List<Reporte> reportes, String path) throws PdfException;
}
