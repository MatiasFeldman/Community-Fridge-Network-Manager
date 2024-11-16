package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraOutputDTO;
import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteOutputDTO;
import ar.edu.utn.frba.dds.dtos.visita_heladera.VisitaHeladeraOutputDTO;
import ar.edu.utn.frba.dds.exceptions.ReportesProblemaException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteFallas;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteMovimientoViandas;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteViandasDonadas;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.visitasHeladeras.VisitaHeladeraRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ReportesController {
    private ReporteFallas reporteFallas;
    private ReporteViandasDonadas reporteViandasDonadas;
    private ReporteMovimientoViandas reporteMovimientoViandas;

    private static String reportesBasePath = "src/main/java/ar/edu/utn/frba/dds/reportesDinamicos/";

    public ReportesController() {
        this.reporteFallas = new ReporteFallas(ServiceLocator.instanceOf(IncidentesRepository.class), ServiceLocator.instanceOf(HeladerasRepository.class));
        this.reporteViandasDonadas = new ReporteViandasDonadas(ServiceLocator.instanceOf(HumanosRepository.class), ServiceLocator.instanceOf(DonacionesDeViandaRepository.class));
        this.reporteMovimientoViandas = new ReporteMovimientoViandas(ServiceLocator.instanceOf(HeladerasRepository.class));
    }

    public void generarReporteDeTodos(Context context) {
        try {
            // Obtener el directorio más reciente (con el nombre de fecha más alta)
            File latestDir = Files.list(Paths.get(reportesBasePath))
                    .filter(Files::isDirectory)
                    .map(Path::toFile)
                    .max(Comparator.comparing(File::getName))
                    .orElseThrow(() -> new RuntimeException("No se encontraron carpetas de reportes"));

            // Generar los PDFs individuales
            Path reporteFallasPath = Paths.get(latestDir.getPath(), "Reporte_de_fallas.pdf");
            Path reporteDonacionPath = Paths.get(latestDir.getPath(), "Reporte_de_viandas_donadas.pdf");
            Path reporteMovimientoPath = Paths.get(latestDir.getPath(), "Reporte_de_viandas_por_heladera.pdf");

            byte[] pdfContentFallas = Files.readAllBytes(reporteFallasPath);
            byte[] pdfContentDonacion = Files.readAllBytes(reporteDonacionPath);
            byte[] pdfContentMovimiento = Files.readAllBytes(reporteMovimientoPath);

            // Crear un ByteArrayOutputStream para el ZIP
            ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
            try (ZipOutputStream zipStream = new ZipOutputStream(zipOutputStream)) {
                // Agregar cada PDF al ZIP
                addToZip(zipStream, "Reporte_de_fallas.pdf", pdfContentFallas);
                addToZip(zipStream, "Reporte_de_viandas_donadas.pdf", pdfContentDonacion);
                addToZip(zipStream, "Reporte_de_movimiento_viandas.pdf", pdfContentMovimiento);
            }

            // Configurar la respuesta para la descarga del archivo ZIP
            context.contentType("application/zip");
            context.header("Content-Disposition", "attachment; filename=\"reporte_todos_" + LocalDate.now() + ".zip\"");
            context.result(zipOutputStream.toByteArray());

        } catch (Exception e) {
            // Registrar la excepción en el log
            throw new ReportesProblemaException("Error al descargar el reporte: " + e.getMessage());
        }
    }


    public void generarReporteDeFallas(Context context) {
        try {
            // Obtener el directorio más reciente (con el nombre de fecha más alta)
            File latestDir = Files.list(Paths.get(reportesBasePath))
                    .filter(Files::isDirectory)
                    .map(Path::toFile)
                    .max(Comparator.comparing(File::getName))
                    .orElseThrow(() -> new RuntimeException("No se encontraron carpetas de reportes"));

            // Obtener el archivo "Reporte_de_fallas" dentro del directorio más reciente
            Path reporteFallasPath = Paths.get(latestDir.getPath(), "Reporte_de_fallas.pdf");

            if (!Files.exists(reporteFallasPath)) {
                throw new ReportesProblemaException("El archivo 'Reporte_de_fallas.pdf' no existe en el directorio más reciente");
            }

            // Leer el archivo en bytes
            byte[] pdfContent = Files.readAllBytes(reporteFallasPath);

            // Configurar la respuesta para la descarga del archivo
            context.contentType("application/pdf");
            context.header("Content-Disposition", "attachment; filename=\"reporte_fallas_" + LocalDate.now() + ".pdf\"");
            context.result(pdfContent);

        } catch (Exception e) {
            // Registrar la excepción en el log
            throw new ReportesProblemaException("Error al descargar el reporte: " + e.getMessage());
        }
    }

    public void generarReporteDeViandasDonadas(Context context) {
        try {
            // Obtener el directorio más reciente (con el nombre de fecha más alta)
            File latestDir = Files.list(Paths.get(reportesBasePath))
                    .filter(Files::isDirectory)
                    .map(Path::toFile)
                    .max(Comparator.comparing(File::getName))
                    .orElseThrow(() -> new RuntimeException("No se encontraron carpetas de reportes"));

            // Obtener el archivo "Reporte_de_fallas" dentro del directorio más reciente
            Path reporteDonacionPath = Paths.get(latestDir.getPath(), "Reporte_de_viandas_donadas.pdf");

            if (!Files.exists(reporteDonacionPath)) {
                throw new ReportesProblemaException("El archivo 'Reporte_de_viandas_donadas.pdf' no existe en el directorio más reciente");
            }

            // Leer el archivo en bytes
            byte[] pdfContent = Files.readAllBytes(reporteDonacionPath);

            // Configurar la respuesta para la descarga del archivo
            context.contentType("application/pdf");
            context.header("Content-Disposition", "attachment; filename=\"Reporte_de_viandas_donadas_" + LocalDate.now() + ".pdf\"");
            context.result(pdfContent);

        } catch (Exception e) {
            // Registrar la excepción en el log
            throw new ReportesProblemaException("Error al descargar el reporte: " + e.getMessage());
        }
    }

    public void generarReporteDeMovimientoViandas(Context context) {
        try {
            // Obtener el directorio más reciente (con el nombre de fecha más alta)
            File latestDir = Files.list(Paths.get(reportesBasePath))
                    .filter(Files::isDirectory)
                    .map(Path::toFile)
                    .max(Comparator.comparing(File::getName))
                    .orElseThrow(() -> new RuntimeException("No se encontraron carpetas de reportes"));

            // Obtener el archivo "Reporte_de_fallas" dentro del directorio más reciente
            Path reporteMovimientoPath = Paths.get(latestDir.getPath(), "Reporte_de_viandas_por_heladera.pdf");

            if (!Files.exists(reporteMovimientoPath)) {
                throw new ReportesProblemaException("El archivo 'Reporte_de_viandas_por_heladera.pdf' no existe en el directorio más reciente");
            }

            // Leer el archivo en bytes
            byte[] pdfContent = Files.readAllBytes(reporteMovimientoPath);

            // Configurar la respuesta para la descarga del archivo
            context.contentType("application/pdf");
            context.header("Content-Disposition", "attachment; filename=\"Reporte_de_viandas_por_heladera_" + LocalDate.now() + ".pdf\"");
            context.result(pdfContent);

        } catch (Exception e) {
            // Registrar la excepción en el log
            throw new ReportesProblemaException("Error al descargar el reporte: " + e.getMessage());
        }
    }

    public void detalleInicidenteView(Context ctx){

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Incidentes de Heladera");

        Long id = Long.parseLong(ctx.pathParam("id"));
        Optional<Heladera> buscada = ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(id);

        if (buscada.isEmpty()){
            ctx.status(404);
            ctx.redirect("/not-found");
        } else{
            Heladera heladera = buscada.get();
            model.put("heladera", HeladeraOutputDTO.of(heladera));
            List<Incidente> incidentes = ServiceLocator.instanceOf(IncidentesRepository.class).buscarTodosPorHeladera(buscada.get());
            List<IncidenteOutputDTO> incidentes_dto = new ArrayList<>();
            incidentes.forEach(i -> incidentes_dto.add(IncidenteOutputDTO.of(i)));


            model.put("incidentes", incidentes_dto);



            RenderUtils.renderizar(ctx,"heladeras/detalle_alertas.hbs", model);
        }


    }

    private void addToZip(ZipOutputStream zipStream, String fileName, byte[] content) throws IOException {
        ZipEntry entry = new ZipEntry(fileName);
        zipStream.putNextEntry(entry);
        zipStream.write(content);
        zipStream.closeEntry();
    }

    public void detalleVisitasView(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Visitas de Heladera");

        Long id = Long.parseLong(ctx.pathParam("id"));
        Optional<Heladera> buscada = ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(id);

        if (buscada.isEmpty()) {
            ctx.status(404);
            ctx.redirect("/not-found");
        } else {
            Heladera heladera = buscada.get();
            List<VisitaAHeladera> visitas = ServiceLocator.instanceOf(VisitaHeladeraRepository.class).buscarPorHeladera(buscada.get());
            List<VisitaHeladeraOutputDTO> visitas_dto = new ArrayList<>();
            visitas.forEach(v -> visitas_dto.add(VisitaHeladeraOutputDTO.of(v)));

            model.put("heladera", HeladeraOutputDTO.of(heladera));
            model.put("visitas", visitas_dto);
            RenderUtils.renderizar(ctx, "heladeras/detalle-visitas.hbs", model);
        }
    }
}
