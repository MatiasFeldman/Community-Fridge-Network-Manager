package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registro_usuario.TarjetaRepetidaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class TarjetaRepetidaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(TarjetaRepetidaException.class, (e, ctx) -> {
            ctx.status(400);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Vulnerable");
            model.put("errorTarjeta", e.getMessage());

            ctx.render("colaboraciones/registro-vulnerable.hbs", model);
        });
    }
}
