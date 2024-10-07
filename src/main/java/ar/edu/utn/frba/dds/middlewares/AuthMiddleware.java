package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.exceptions.NoSesionIniciadaException;
import io.javalin.Javalin;
import io.javalin.http.Context;


import java.util.List;

public class AuthMiddleware implements IMiddleware {
    @Override
    public void apply(Javalin app) {
        app.beforeMatched(ctx -> {
            if(esRutaPublica(ctx)){
                return;
            }
            String user = ctx.sessionAttribute("user");
            if(user == null){
                    //throw new NoSesionIniciadaException();
                ctx.redirect("/login");
            }

            List<String> roles = ctx.sessionAttribute("roles");

            List<String> rolesRequeridos = ctx.attribute("rolesRequeridos");

            if(rolesRequeridos != null && !rolesRequeridos.isEmpty() &&
                    (roles == null || roles.isEmpty() || roles.stream().noneMatch(rolesRequeridos::contains))) {
                throw new AccessDeniedException();
            }
        });
    }

    private boolean esRutaPublica(Context ctx) {
        return ctx.path().equals("/") || ctx.path().equals("/login");
    }
}
