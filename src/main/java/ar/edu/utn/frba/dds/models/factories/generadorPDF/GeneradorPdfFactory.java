package ar.edu.utn.frba.dds.models.factories.generadorPDF;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;

public class GeneradorPdfFactory {
    public static GeneradorPDF create() {return new PDFgenerator();
    }
}
