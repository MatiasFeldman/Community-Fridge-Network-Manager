package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.InvalidContribucionException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

public class InvalidContribucionHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(InvalidContribucionException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result(e.getMessage());
        });
    }
}
