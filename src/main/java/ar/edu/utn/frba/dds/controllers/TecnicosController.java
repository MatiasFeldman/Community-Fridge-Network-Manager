package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.TecnicosRepository;
import ar.edu.utn.frba.dds.models.repositories.visitasHeladeras.VisitaHeladeraRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TecnicosController {

    public static void formSolucionIncidente(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        Long idHeladera;

        try {
            idHeladera = Long.parseLong(ctx.pathParam("id"));
            Optional<Heladera> heladera = ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(idHeladera);


            // Si no se encuentra la heladera, redirigir a la página de error
            if (heladera.isEmpty()) {
                Map<String, Object> model2 = new HashMap<>();
                model2.put("error", "La heladera con el ID proporcionado no existe.");
                model2.put("paginaAnterior", "/heladeras");
                ctx.status(404).render("400Personalizado.hbs", model2);
                return;
            }

            // Pasar la heladera al modelo
            model.put("heladera", heladera.get());
            model.put("noIncidentes", Boolean.parseBoolean(ctx.queryParam("noHayIncidentes")));

        } catch (NumberFormatException e) {
            // Manejo de error para un ID no válido
            Map<String, Object> model2 = new HashMap<>();
            model2.put("error", "El ID de la heladera debe ser un número válido.");
            model2.put("paginaAnterior", "/heladeras");
            ctx.status(400).render("400Personalizado.hbs", model2);
            return;

        } catch (Exception e) {
            // Manejo genérico de errores
            Map<String, Object> model2 = new HashMap<>();
            model2.put("error", "Ocurrió un error inesperado. Por favor, inténtelo más tarde.");
            model2.put("paginaAnterior", "/heladeras");
            ctx.status(500).render("400Personalizado.hbs", model2);
            return;
        }

        // Renderizar la vista si todo está bien
        model.put("titulo", "Visita a heladera");
        RenderUtils.renderizar(ctx, "heladeras/form-visita-heladera.hbs", model);
    }

    public static void registrarVisita( Context ctx) {
        //TODO: manejo de errores y tener en cuenta que revise la fecha y hora tiene que ser anteorires a la del momento
        Long usuarioId = ctx.sessionAttribute("id");
        Optional<Tecnico> tecnico = ServiceLocator.instanceOf(TecnicosRepository.class).buscarPorIdUsuario(usuarioId);
        Long heladera_id = Long.parseLong(ctx.pathParam("id"));
        Optional<Heladera> heladera =  ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(heladera_id);
        List<Incidente> incidentes = ServiceLocator.instanceOf(IncidentesRepository.class).buscarTodosPorHeladera(heladera.get());
        Optional<Incidente> incidente = incidentes.stream().filter(incidente2 ->  !incidente2.getResuelto()).findFirst();//TODO: estoy suponiendo que solo pude tener un incidente activo
        if(!incidente.isPresent() || incidente.get() == null ){
            ctx.redirect("/heladeras/"+String.valueOf(heladera_id)+"/visita?noHayIncidentes=true");
            return;
        }
        LocalDate fecha = LocalDate.parse(ctx.formParam("fechaVisita"));
        LocalTime hora = LocalTime.parse(ctx.formParam("horaVisita")) ;
        LocalDateTime fechaYHora = LocalDateTime.of(fecha,hora);
        Boolean resuelto = Boolean.parseBoolean(ctx.formParam("resolucionIncidente"));
        String descripcion = ctx.formParam("descripcion");
        VisitaAHeladera visita = VisitaAHeladera.crear(incidente.get(),tecnico.get(),fechaYHora,resuelto,descripcion);
        if(resuelto){
            incidente.get().setResuelto(true);
            incidente.get().setFechaResuelto(LocalDateTime.now());
            ServiceLocator.instanceOf(IncidentesRepository.class).modificar(incidente.get());
            heladera.get().activar();
            ServiceLocator.instanceOf(HeladerasRepository.class).modificar(heladera.get());
        }
        ServiceLocator.instanceOf(VisitaHeladeraRepository.class).guardar(visita);

        // Obtener archivo de imagen (si se cargó uno)
        UploadedFile imagenVisita = ctx.uploadedFile("foto");
        String rutaImagen = null;

        if (imagenVisita != null && imagenVisita.size() > 0) {
            // Definir el nombre del archivo y la ruta de almacenamiento
            String nombreArchivo = "visita_" + visita.getId();

            String directorioCompleto = Paths.get("src", "main", "java","ar","edu","utn","frba","dds", "imagenesDinamicas","fotosVisitas" ,nombreArchivo).toString();
            rutaImagen = "/imagenes/fotosVisitas/"+nombreArchivo;

            // Guardar la imagen en el servidor
            try (InputStream inputStream = imagenVisita.content()) {
                Files.copy(inputStream, Paths.get(directorioCompleto), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new SolicitudIncorrectaException();
            }
            visita.setFoto(rutaImagen);
            ServiceLocator.instanceOf(VisitaHeladeraRepository.class).actualizar(visita);
        }
        ctx.redirect("/heladeras");
    }
}
