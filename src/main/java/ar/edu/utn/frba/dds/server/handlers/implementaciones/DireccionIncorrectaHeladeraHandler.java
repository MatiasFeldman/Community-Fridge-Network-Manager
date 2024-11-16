package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroHeladera.DireccionIncorrectaHeladeraException;
import ar.edu.utn.frba.dds.exceptions.registroHeladera.TemperaturaIncorrectaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class DireccionIncorrectaHeladeraHandler implements IHandler {
    public void setHandle(Javalin app) {
        app.exception(DireccionIncorrectaHeladeraException.class, (e, ctx) -> {
            String nombre = ctx.formParam("nombre");
            String capacidadMaximaStr = ctx.formParam("capacidadMaxima");;
            String temperaturaMaximaStr = ctx.formParam("temperaturaMaxima");
            String temperaturaMinimaStr = ctx.formParam("temperaturaMinima");

            ctx.status(401);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Hacerse cargo de heladera");
            model.put("errorDireccion", e.getMessage());

            model.put("nombre", nombre);
            model.put("capacidadMaxima", capacidadMaximaStr);
            model.put("temperaturaMaxima", temperaturaMaximaStr);
            model.put("temperaturaMinima", temperaturaMinimaStr);

            ctx.render("colaboraciones/heladera-a-cargo.hbs", model);
        });
    }
}
