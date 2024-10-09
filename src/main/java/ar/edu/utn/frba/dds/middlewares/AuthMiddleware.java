package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.exceptions.NoSesionIniciadaException;
import io.javalin.Javalin;

import io.javalin.security.RouteRole;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthMiddleware implements IMiddleware {
    @Override
    public void apply(Javalin app) {
        app.beforeMatched(ctx -> {
            Set<RouteRole> rolesRequeridos = ctx.routeRoles();

            if(!rolesRequeridos.isEmpty()){
                Long userId = ctx.sessionAttribute("user");
                if(userId == null){
                    throw new NoSesionIniciadaException();
                }

                List<String> roles = ctx.sessionAttribute("roles");

                // convierto roles requeridos a strings para comparar
                Set<String> rolesRequeridosStrings = rolesRequeridos.stream()
                        .map(RouteRole::toString)
                        .collect(Collectors.toSet());

                if (roles == null || roles.isEmpty() || roles.stream().noneMatch(rolesRequeridosStrings::contains)) {
                    throw new AccessDeniedException();
                }
            }
        });
    }
}
