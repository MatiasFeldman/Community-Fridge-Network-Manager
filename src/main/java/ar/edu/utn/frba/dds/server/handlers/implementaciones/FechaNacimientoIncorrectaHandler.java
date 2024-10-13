package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.FechaNacimientoIncorrectaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class FechaNacimientoIncorrectaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(FechaNacimientoIncorrectaException.class, (e, ctx) -> {
            ctx.status(401);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro de persona vulnerable");
            model.put("fechaIncorrecta", true);

            ctx.render("colaboraciones/registro-vulnerable.hbs", model);
        });
    }
}
