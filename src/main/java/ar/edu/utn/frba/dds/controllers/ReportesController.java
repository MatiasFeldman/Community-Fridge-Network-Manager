package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteFallas;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteMovimientoViandas;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteViandasDonadas;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.http.Context;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReportesController {
    private ReporteFallas reporteFallas;
    private ReporteViandasDonadas reporteViandasDonadas;
    private ReporteMovimientoViandas reporteMovimientoViandas;

    public ReportesController() {
        this.reporteFallas = new ReporteFallas(ServiceLocator.instanceOf(IncidentesRepository.class), ServiceLocator.instanceOf(HeladerasRepository.class));
        this.reporteViandasDonadas = new ReporteViandasDonadas(ServiceLocator.instanceOf(HumanosRepository.class), ServiceLocator.instanceOf(DonacionesDeViandaRepository.class));
        this.reporteMovimientoViandas = new ReporteMovimientoViandas(ServiceLocator.instanceOf(HeladerasRepository.class));
    }

    public void generarReporteDeTodos(Context context) {
        try {
            PDFgenerator pdfgenerator = ServiceLocator.instanceOf(PDFgenerator.class);
            ByteArrayOutputStream pdf = pdfgenerator.generarPdfParaEnviar(List.of(reporteFallas, reporteViandasDonadas, reporteMovimientoViandas));

            context.contentType("application/pdf");
            context.header("Content-Disposition", "attachment; filename=\"reporte_todos_" + LocalDate.now() + ".pdf\"");
            context.result(pdf.toByteArray());
        } catch (Exception e) {
            // Registrar la excepción en el log
            e.printStackTrace();
            // Enviar una respuesta de error
            context.status(500).result("Error al generar el reporte: " + e.getMessage());
        }
    }


    public void generarReporteDeFallas(Context context) {
        try {
            PDFgenerator pdfgenerator = ServiceLocator.instanceOf(PDFgenerator.class);

            ByteArrayOutputStream pdf = pdfgenerator.generarPdfParaEnviar(List.of(reporteFallas));

            context.contentType("application/pdf");
            context.header("Content-Disposition", "attachment; filename=\"reporte_todos_" + LocalDate.now() + ".pdf\"");
            context.result(pdf.toByteArray());
        } catch (Exception e) {
            // Registrar la excepción en el log
            e.printStackTrace();
            // Enviar una respuesta de error
            context.status(500).result("Error al generar el reporte: " + e.getMessage());
        }
    }

    public void generarReporteDeViandasDonadas(Context context) {
        try {
            PDFgenerator pdfgenerator = ServiceLocator.instanceOf(PDFgenerator.class);

            ByteArrayOutputStream pdf = pdfgenerator.generarPdfParaEnviar(List.of(reporteViandasDonadas));

            context.contentType("application/pdf");
            context.header("Content-Disposition", "attachment; filename=\"reporte_todos_" + LocalDate.now() + ".pdf\"");
            context.result(pdf.toByteArray());
        } catch (Exception e) {
            // Registrar la excepción en el log
            e.printStackTrace();
            // Enviar una respuesta de error
            context.status(500).result("Error al generar el reporte: " + e.getMessage());
        }
    }

    public void generarReporteDeMovimientoViandas(Context context) {
        try {
            PDFgenerator pdfgenerator = ServiceLocator.instanceOf(PDFgenerator.class);

            ByteArrayOutputStream pdf = pdfgenerator.generarPdfParaEnviar(List.of(reporteMovimientoViandas));

            context.contentType("application/pdf");
            context.header("Content-Disposition", "attachment; filename=\"reporte_todos_" + LocalDate.now() + ".pdf\"");
            context.result(pdf.toByteArray());
        } catch (Exception e) {
            // Registrar la excepción en el log
            e.printStackTrace();
            // Enviar una respuesta de error
            context.status(500).result("Error al generar el reporte: " + e.getMessage());
        }
    }

    public void detalleInicidenteView(Context ctx){

        Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
        model.put("titulo", "incidente de Heladera");

        String idParam = ctx.pathParam("id");
        Long id = Long.parseLong(idParam);
        Optional<Heladera> buscada = ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(id);
        List<Incidente> incidentes= ServiceLocator.instanceOf(IncidentesRepository.class).buscarTodosPorHeladera(buscada.get());
        model.put("incidentes",incidentes);
        ctx.render("heladeras/detalle_alertas.hbs", model);
    }
}
