package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.*;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import com.itextpdf.text.pdf.PdfException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;

import static org.mockito.Mockito.times;

public class GenerarReporteTest {
    @Test
    public void generarReporteTest() throws PdfException {

        PDFgenerator pdfGeneratorMock = Mockito.mock(PDFgenerator.class);
        IncidentesRepository incidentesRepository = Mockito.mock(IncidentesRepository.class);
        HumanosRepository humanosRepository = Mockito.mock(HumanosRepository.class);
        PersonasVulnerablesRepository personasVulnerablesRepository = Mockito.mock(PersonasVulnerablesRepository.class);



        String filePath = "/Users/matifeldman/Documentos/DDS";


        // Crear instancias de reportes usando los repositorios mock
        List<Reporte> reportes = List.of(
                new ReporteFallas(incidentesRepository),
                new ReporteViandasDonadas(humanosRepository),
                new ReporteMovimientoViandas(humanosRepository, personasVulnerablesRepository));

        pdfGeneratorMock.generarPDF(reportes, filePath);

        // Verificar que el método generarPDF fue llamado con los argumentos correctos
        Mockito.verify(pdfGeneratorMock, times(1)).generarPDF(reportes, filePath);
    }
}
