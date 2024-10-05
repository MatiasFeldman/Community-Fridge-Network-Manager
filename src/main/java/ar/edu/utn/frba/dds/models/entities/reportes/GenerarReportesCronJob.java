package ar.edu.utn.frba.dds.models.entities.reportes;


import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.itextpdf.text.pdf.PdfException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class GenerarReportesCronJob implements Runnable {
    private GeneradorPDF generadorPDF;
    private String filePath;


    @Override
    public void run() {
        System.out.println("Generando reportes...");
        List<Reporte> listaReportes = new ArrayList<>();
        //listaReportes.add(new ReporteMock("Reporte 1", "Este es el contenido del primer reporte."));
        listaReportes.add(new ReporteFallas(ServiceLocator.instanceOf(IncidentesRepository.class), ServiceLocator.instanceOf(HeladerasRepository.class)));
        listaReportes.add(new ReporteViandasDonadas(ServiceLocator.instanceOf(HumanosRepository.class), ServiceLocator.instanceOf(DonacionesDeViandaRepository.class)));
        listaReportes.add(new ReporteMovimientoViandas(ServiceLocator.instanceOf(HeladerasRepository.class)));


        try {
            generadorPDF.guardarPdfEnPath(listaReportes, filePath);
            System.out.println("Reportes generados con éxito");
        } catch (PdfException e) {
            e.printStackTrace();
        }
    }
}