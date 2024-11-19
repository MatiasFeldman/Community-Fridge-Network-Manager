package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.server.handlers.IHandler;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class dondeDonarDireccionIncorrectaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(ar.edu.utn.frba.dds.exceptions.dondeDonarDireccionIncorrectaException.class, (e, ctx) -> {
            ctx.status(400);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Donde donar");
            model.put("error", e.getMessage());

            RenderUtils.renderizar(ctx,"donde-donar.hbs", model);
        });
    }
}
