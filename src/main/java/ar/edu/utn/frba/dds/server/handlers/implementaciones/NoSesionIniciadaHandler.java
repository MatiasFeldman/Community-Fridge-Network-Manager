package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.NoSesionIniciadaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

public class NoSesionIniciadaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(NoSesionIniciadaException.class, (e, context) -> {
            context.status(401);
            context.redirect("/login");
        });
    }
}
