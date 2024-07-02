package ar.edu.utn.frba.dds.models.main;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.IGeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.GenerarReportesCronJob;
import ar.edu.utn.frba.dds.models.entities.reportes.IReporte;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteMock;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.dao.HumanosDAO;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import com.itextpdf.text.pdf.PdfException;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainReportes {
    GenerarReportesCronJob generarReportesCronJob;

    public MainReportes(GenerarReportesCronJob generarReportesCronJob) {
        this.generarReportesCronJob = generarReportesCronJob;
    }

    public void iniciarCronJob() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long intervalo = 1; // Cada 7 días (una semana)
        TimeUnit unit = TimeUnit.MINUTES;

        scheduler.scheduleAtFixedRate(generarReportesCronJob, 0, intervalo, unit);
    }

    public static void main(String[] args) {
        String filePath = "";
        IGeneradorPDF generadorPDF = new PDFgenerator();
        IncidentesRepository incidentesRepository = new IncidentesRepository(); // Cambiar por el repositorio real
        HumanosRepository humanosRepository = new HumanosRepository(null);  // Cambiar por el repositorio real
        PersonasVulnerablesRepository personasVulnerablesRepository = new PersonasVulnerablesRepository(null);  // Cambiar por el repositorio real

        GenerarReportesCronJob reportesCronJob = new GenerarReportesCronJob(generadorPDF, filePath, incidentesRepository, humanosRepository, personasVulnerablesRepository);

        MainReportes main = new MainReportes(reportesCronJob);
        main.iniciarCronJob();
    }
}

    /* public static void main(String[] args) throws PdfException {
        String path = "/Users/matifeldman/Documentos/DDS";

        IGeneradorPDF pdfGenerator = new PDFgenerator();


        GenerarReportesCronJob reportesCronJob = new GenerarReportesCronJob(pdfGenerator, path);

        MainReportes main = new MainReportes(reportesCronJob);
        main.iniciarCronJob();
    } */

