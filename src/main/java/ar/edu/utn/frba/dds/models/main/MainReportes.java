package ar.edu.utn.frba.dds.models.main;


import ar.edu.utn.frba.dds.models.entities.helpers.reportes.IGeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.*;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
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

    /*
    public static void main(String[] args) throws PdfException {
        String path = "";
        IncidentesRepository incidentesRepository = new IncidentesRepository(); // Cambiar por el repositorio real
        IncidentesRepository humanosRepository = new HumanosRepository(); // Cambiar por el repositorio real
        PersonasVulnerablesRepository personasVulnerablesRepository = new PersonasVulnerablesRepository(); // Cambiar por el repositorio real

        List<IReporte> reportes = List.of(
                new ReporteFallas(incidentesRepository),
                new ReporteViandasDonadas(humanosRepository),
                new ReporteMovimientoViandas(humanosRepository, personasVulnerablesRepository));

        IGeneradorPDF pdfGenerator = new PDFgenerator();
        pdfGenerator.generarPDF(reportes, path);
    } */
}
