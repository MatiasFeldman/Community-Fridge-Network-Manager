package ar.edu.utn.frba.dds.models.main;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.GenerarReportesCronJob;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainReportes {
    GenerarReportesCronJob generarReportesCronJob;

    public MainReportes(GenerarReportesCronJob generarReportesCronJob) {
        this.generarReportesCronJob = generarReportesCronJob;
    }

    public void ejecutarUnaVez() {
        generarReportesCronJob.run();
    }

    // El método main acepta los repositorios como parámetros
    public static void main(String[] args) {
        // Aquí se reciben las instancias de los repositorios desde otro lugar
        HumanosRepository humanosRepository = new HumanosRepository(null);
        IncidentesRepository incidentesRepository = new IncidentesRepository();
        PersonasVulnerablesRepository personasVulnerablesRepository = new PersonasVulnerablesRepository(null);

        String filePath = "";
        GeneradorPDF generadorPDF = new PDFgenerator();

        GenerarReportesCronJob reportesCronJob = new GenerarReportesCronJob(generadorPDF, filePath, incidentesRepository, humanosRepository, personasVulnerablesRepository);

        MainReportes main = new MainReportes(reportesCronJob);
        main.ejecutarUnaVez();
    }
}

