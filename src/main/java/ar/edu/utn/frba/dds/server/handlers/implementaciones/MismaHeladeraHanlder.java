package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.DistribucionViandas.MismaHeladeraException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MismaHeladeraHanlder implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(MismaHeladeraException.class, (e, ctx) -> {
            ctx.status(401);

            HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
            List<Heladera> heladeras = heladerasRepository.buscarTodos();

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Distribuir viandas");
            model.put("heladeras", heladeras);
            model.put("mismaHeladeraError", e.getMessage());

            ctx.render("colaboraciones/distribucion-de-viandas.hbs", model);
        });
    }
}
