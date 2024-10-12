package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.RegistroPersonaVulnerableIncompletoException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class RegistroPersonaVulnerableIncompletoHandler implements IHandler {
    public void setHandle(Javalin app) {
        app.exception(RegistroPersonaVulnerableIncompletoException.class, (e, ctx) -> {
            ctx.status(401);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro de persona vulnerable");

            ctx.render("colaboraciones/registro-vulnerable.hbs", model);
        });
    }
}
