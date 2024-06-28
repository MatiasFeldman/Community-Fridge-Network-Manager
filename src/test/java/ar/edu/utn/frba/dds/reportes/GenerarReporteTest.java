package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.IGeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.reportes.*;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import com.itextpdf.text.pdf.PdfException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;

public class GenerarReporteTest {
    @Test
    public void generarReporteTest() throws PdfException {

        IGeneradorPDF pdfGeneratorMock = Mockito.mock(IGeneradorPDF.class);
        IncidentesRepository incidentesRepository = Mockito.mock(IncidentesRepository.class);
        HumanosRepository humanosRepository = Mockito.mock(HumanosRepository.class);


        GeneradorPDF generarReporte = new GeneradorPDF(pdfGeneratorMock);


        String filePath = "/Users/matifeldman/Documentos/DDS";


        // Crear instancias de reportes usando los repositorios mock
        List<IReporte> reportes = List.of(
                new ReporteFallas(incidentesRepository),
                new ReporteViandasDonadas(humanosRepository));

        generarReporte.generarPDF(reportes, filePath);

        // Verificar que el método generarPDF fue llamado con los argumentos correctos
        Mockito.verify(pdfGeneratorMock, times(1)).generarPDF(reportes, filePath);
    }
}
