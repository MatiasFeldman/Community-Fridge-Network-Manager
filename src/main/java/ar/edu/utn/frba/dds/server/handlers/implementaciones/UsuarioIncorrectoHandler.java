package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.login.UsuarioIncorrectoException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class UsuarioIncorrectoHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(UsuarioIncorrectoException.class, (e, ctx) -> {
            ctx.status(401);

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Login");
            model.put("errorUsuario", true);

            ctx.render("login.hbs",model);
        });
    }
}
