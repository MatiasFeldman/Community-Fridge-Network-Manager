package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.DistribucionViandas.CantidadViandasIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroHeladera.TemperaturaIncorrectaException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CantidadViandasIncorrectaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(CantidadViandasIncorrectaException.class, (e, ctx) -> {
            ctx.status(401);

            HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
            List<Heladera> heladeras = heladerasRepository.buscarTodos();

            heladeras.forEach(heladera -> {
                String mensajeDisponibilidad;
                int cantActual = heladera.cantActual();

                if (cantActual == 0) {
                    mensajeDisponibilidad = "¡Sin viandas disponibles!";
                } else if (cantActual <= 4) {
                    mensajeDisponibilidad = "¡Quedan menos de 5 viandas!";
                } else if (cantActual <= 10) {
                    mensajeDisponibilidad = "¡Quedan menos de 10 viandas disponibles!";
                } else if (cantActual <= 20) {
                    mensajeDisponibilidad = "Stock moderado de viandas";
                } else {
                    mensajeDisponibilidad = "Suficiente stock de viandas";
                }

                heladera.setMensajeDisponiblididad(mensajeDisponibilidad);
            });

            String motivoDistribucion = ctx.formParam("motivoDistribucion");
            String heladeraOrigenId = ctx.formParam("heladeraOrigen");
            String heladeraDestinoId = ctx.formParam("heladeraDestino");

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Distribuir viandas");
            model.put("heladeras", heladeras);
            model.put("errorCantidad", e.getMessage());
            model.put("motivoDistribucion", motivoDistribucion);
            model.put("heladeraOrigenId", heladeraOrigenId);
            model.put("heladeraDestinoId", heladeraDestinoId);

            ctx.render("colaboraciones/distribucion-de-viandas.hbs", model);
        });
    }
}
