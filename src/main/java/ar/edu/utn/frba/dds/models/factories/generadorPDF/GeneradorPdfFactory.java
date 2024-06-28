package ar.edu.utn.frba.dds.models.factories.generadorPDF;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.IGeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;

public class GeneradorPdfFactory {
    public static IGeneradorPDF create() {return new PDFgenerator();
    }
}
