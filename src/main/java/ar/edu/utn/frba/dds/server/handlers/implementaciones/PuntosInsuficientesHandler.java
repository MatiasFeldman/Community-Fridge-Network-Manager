package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;

import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;


public class PuntosInsuficientesHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(PuntosInsuficientesException.class, (e, ctx) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Puntos Insuficientes");
            ctx.render("colaboraciones/puntos-insuficientes.hbs", model);
        });
    }
}
