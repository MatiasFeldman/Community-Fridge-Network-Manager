package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.ContraseniaIncorrectaException;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class ContraseniaIncorrectaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(ContraseniaIncorrectaException.class, (e, ctx) -> {
            ctx.status(401);

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Login");
            model.put("errorContrasenia", true);

            ctx.render("login.hbs",model);
        });
    }
}
