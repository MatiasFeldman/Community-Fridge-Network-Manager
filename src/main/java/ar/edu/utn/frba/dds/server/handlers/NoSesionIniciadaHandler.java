package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.NoSesionIniciadaException;
import io.javalin.Javalin;

public class NoSesionIniciadaHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(NoSesionIniciadaException.class, (e, context) -> {
            context.redirect("/login");
        });
    }
}
