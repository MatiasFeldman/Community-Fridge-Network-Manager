package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.APIIntegracionSinConexionException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class APIIntegracionSinConexionHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(APIIntegracionSinConexionException.class, (e, ctx) -> {
            ctx.status(500);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "API de integración sin conexión");
            model.put("sinConexion", true);

            ctx.render("donde-donar-resultados.hbs", model);
        });
    }
}
