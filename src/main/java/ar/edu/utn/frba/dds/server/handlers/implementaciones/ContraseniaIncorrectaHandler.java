package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.login.ContraseniaIncorrectaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class ContraseniaIncorrectaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(ContraseniaIncorrectaException.class, (e, ctx) -> {
            ctx.status(401);

            String username = ctx.formParam("username");

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Login");
            model.put("errorContrasenia", true);
            model.put("username", username);

            ctx.render("login.hbs",model);
        });
    }
}
