package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.*;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.itextpdf.text.pdf.PdfException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;

import static org.mockito.Mockito.times;

public class GenerarReporteTest {
    @Test
    public void generarReporteTest(){

        PDFgenerator pdfGeneratorMock = Mockito.mock(PDFgenerator.class);

        ReporteFallas reporteFallas = new ReporteFallas(ServiceLocator.instanceOf(IncidentesRepository.class), ServiceLocator.instanceOf(HeladerasRepository.class));
        ReporteViandasDonadas reporteViandasDonadas = new ReporteViandasDonadas(ServiceLocator.instanceOf(HumanosRepository.class), ServiceLocator.instanceOf(DonacionesDeViandaRepository.class));
        ReporteMovimientoViandas movimientoViandas = new ReporteMovimientoViandas(ServiceLocator.instanceOf(HeladerasRepository.class));
        String filePath = "/Users/juanc/Downloads";


        // Crear instancias de reportes usando los repositorios mock
        List<Reporte> reportes = List.of(
                reporteFallas,
                reporteViandasDonadas,
                movimientoViandas
        );
        pdfGeneratorMock.generarPDF(reportes, filePath);

        // Verificar que el método generarPDF fue llamado con los argumentos correctos
        Mockito.verify(pdfGeneratorMock, times(1)).generarPDF(reportes, filePath);
    }
}
