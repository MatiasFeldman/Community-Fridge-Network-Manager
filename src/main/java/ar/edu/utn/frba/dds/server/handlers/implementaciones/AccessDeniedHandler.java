package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

public class AccessDeniedHandler implements IHandler {

    @Override
    public void setHandle(Javalin app) {
        app.exception(AccessDeniedException.class, (e, context) -> {
            context.status(403);
            context.render("/403.hbs");
        });
    }
}
