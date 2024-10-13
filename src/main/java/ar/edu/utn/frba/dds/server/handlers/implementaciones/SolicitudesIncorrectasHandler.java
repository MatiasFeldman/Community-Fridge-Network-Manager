package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;


public class SolicitudesIncorrectasHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.render("/400.hbs");
        });
    }
}
