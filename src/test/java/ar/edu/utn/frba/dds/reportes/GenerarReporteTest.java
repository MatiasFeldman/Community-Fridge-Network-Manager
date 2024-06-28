package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.IGeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.Reporte;
import com.itextpdf.text.pdf.PdfException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

public class GenerarReporteTest {
    @Test
    public void generarReporteTest() throws PdfException {

        IGeneradorPDF pdfGeneratorMock = Mockito.mock(IGeneradorPDF.class);


        GeneradorPDF generarReporte = new GeneradorPDF(pdfGeneratorMock);


        String filePath = "C:\\Nico\\utn\\3°Año\\Diseño";

        List<Reporte> reportes = new ArrayList<>();
        reportes.add(new Reporte("Reporte 1", "Este es el contenido del primer reporte."));
        reportes.add(new Reporte("Reporte 2", "Este es el contenido del segundo reporte."));
        reportes.add(new Reporte("Reporte 3", "Este es el contenido del tercer reporte."));

        // Ejecutar el método a probar
        generarReporte.generarPDF(reportes, filePath);

        // Verificar que el método generarPDF fue llamado con los argumentos correctos
        Mockito.verify(pdfGeneratorMock, times(1)).generarPDF(reportes, filePath);
    }
}
