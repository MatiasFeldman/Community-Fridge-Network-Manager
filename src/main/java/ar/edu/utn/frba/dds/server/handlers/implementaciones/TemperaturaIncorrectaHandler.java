package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroHeladera.CapacidadIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroHeladera.TemperaturaIncorrectaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class TemperaturaIncorrectaHandler implements IHandler {
    public void setHandle(Javalin app) {
        app.exception(TemperaturaIncorrectaException.class, (e, ctx) -> {
            String nombre = ctx.formParam("nombre");
            String calle = ctx.formParam("calle");
            String provincia = ctx.formParam("provincia");
            String capacidadMaximaStr = ctx.formParam("capacidadMaxima");

            ctx.status(401);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Hacerse cargo de heladera");
            model.put("temperaturaError", true);

            model.put("nombre", nombre);
            model.put("calle", calle);
            model.put("provincia", provincia);
            model.put("capacidadMaxima", capacidadMaximaStr);

            ctx.render("colaboraciones/heladera-a-cargo.hbs", model);
        });
    }
}
