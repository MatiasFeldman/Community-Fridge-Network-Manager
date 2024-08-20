package ar.edu.utn.frba.dds.models.entities.reportes;


import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import com.itextpdf.text.pdf.PdfException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class GenerarReportesCronJob implements Runnable {
    private GeneradorPDF generadorPDF;
    private String filePath;
    private IncidentesRepository incidentesRepository;
    private HumanosRepository humanosRepository;
    private PersonasVulnerablesRepository personasVulnerablesRepository;


    @Override
    public void run() {
        System.out.println("Generando reportes...");
        List<Reporte> listaReportes = new ArrayList<>();
        //listaReportes.add(new ReporteMock("Reporte 1", "Este es el contenido del primer reporte."));
        listaReportes.add(new ReporteFallas(incidentesRepository));
        listaReportes.add(new ReporteViandasDonadas(humanosRepository));
        listaReportes.add(new ReporteMovimientoViandas(humanosRepository, personasVulnerablesRepository));


        try {
            generadorPDF.generarPDF(listaReportes, filePath);
            System.out.println("Reportes generados con éxito");
        } catch (PdfException e) {
            e.printStackTrace();
        }
    }
}