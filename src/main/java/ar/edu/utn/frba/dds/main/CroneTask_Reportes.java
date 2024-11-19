package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.GenerarReportesCronJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CroneTask_Reportes implements Job {
    GenerarReportesCronJob generarReportesCronJob;

    public CroneTask_Reportes(GenerarReportesCronJob generarReportesCronJob) {
        this.generarReportesCronJob = generarReportesCronJob;
    }

    public void ejecutarUnaVez() {
        generarReportesCronJob.run();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String filePath = "src/main/java/ar/edu/utn/frba/dds/reportesDinamicos/";

        String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        filePath += dateFolder + "/";

        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        GeneradorPDF generadorPDF = new PDFgenerator();

        GenerarReportesCronJob reportesCronJob = new GenerarReportesCronJob(generadorPDF, filePath);

        CroneTask_Reportes main = new CroneTask_Reportes(reportesCronJob);
        main.ejecutarUnaVez();
    }
}

