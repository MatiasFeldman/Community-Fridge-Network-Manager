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


public class MainReportes {
    GenerarReportesCronJob generarReportesCronJob;

    public MainReportes(GenerarReportesCronJob generarReportesCronJob) {
        this.generarReportesCronJob = generarReportesCronJob;
    }

    public void ejecutarUnaVez() {
        generarReportesCronJob.run();
    }

    public static void main(String[] args) {
        HumanosRepository humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
        IncidentesRepository incidentesRepository = ServiceLocator.instanceOf(IncidentesRepository.class);
        PersonasVulnerablesRepository personasVulnerablesRepository = ServiceLocator.instanceOf(PersonasVulnerablesRepository.class);
        DistribucionesDeViandasRepository distribucionesDeViandasRepository = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class);
        DonacionesDeViandaRepository donacionesDeViandaRepository = ServiceLocator.instanceOf(DonacionesDeViandaRepository.class);

        String filePath = "";
        GeneradorPDF generadorPDF = new PDFgenerator();

        GenerarReportesCronJob reportesCronJob = new GenerarReportesCronJob(generadorPDF, filePath, incidentesRepository, humanosRepository, personasVulnerablesRepository, donacionesDeViandaRepository, distribucionesDeViandasRepository);

        MainReportes main = new MainReportes(reportesCronJob);
        main.ejecutarUnaVez();
    }
}

