package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.RegistroTarjetaInexistenteException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class RegistroTarjetaInexistenteHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(RegistroTarjetaInexistenteException.class, (e, context) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro de persona vulnerable");
            model.put("errorTarjeta", true);

            context.render("colaboraciones/registro-vulnerable.hbs", model);
        });
    }
}
