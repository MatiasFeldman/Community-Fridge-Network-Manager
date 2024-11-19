package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.exceptions.error404exception;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

public class error404handler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(error404exception.class, (e, context) -> {
            context.status(404);
            context.render("/404.hbs");
        });
    }
}
