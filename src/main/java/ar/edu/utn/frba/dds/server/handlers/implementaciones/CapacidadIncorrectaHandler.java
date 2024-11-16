package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroHeladera.CapacidadIncorrectaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class CapacidadIncorrectaHandler implements IHandler {
    public void setHandle(Javalin app) {
        app.exception(CapacidadIncorrectaException.class, (e, ctx) -> {
            String nombre = ctx.formParam("nombre");
            String calle = ctx.formParam("calle");
            String provincia = ctx.formParam("provincia");
            String temperaturaMaximaStr = ctx.formParam("temperaturaMaxima");
            String temperaturaMinimaStr = ctx.formParam("temperaturaMinima");

            ctx.status(401);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Hacerse cargo de heladera");
            model.put("capacidadError", true);

            model.put("nombre", nombre);
            model.put("calle", calle);
            model.put("provincia", provincia);
            model.put("temperaturaMaxima", temperaturaMaximaStr);
            model.put("temperaturaMinima", temperaturaMinimaStr);

            ctx.render("colaboraciones/heladera-a-cargo.hbs", model);
        });
    }
}
