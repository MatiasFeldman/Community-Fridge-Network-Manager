package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.GenerarReportesCronJob;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainReportes {
    GenerarReportesCronJob generarReportesCronJob;

    public MainReportes(GenerarReportesCronJob generarReportesCronJob) {
        this.generarReportesCronJob = generarReportesCronJob;
    }

    public void ejecutarUnaVez() {
        generarReportesCronJob.run();
    }

    public static void main(String[] args) {
        String filePath = "src/main/java/ar/edu/utn/frba/dds/reportesDinamicos/";

        String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        filePath += dateFolder + "/";

        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        GeneradorPDF generadorPDF = new PDFgenerator();

        GenerarReportesCronJob reportesCronJob = new GenerarReportesCronJob(generadorPDF, filePath);

        MainReportes main = new MainReportes(reportesCronJob);
        main.ejecutarUnaVez();
    }
}

