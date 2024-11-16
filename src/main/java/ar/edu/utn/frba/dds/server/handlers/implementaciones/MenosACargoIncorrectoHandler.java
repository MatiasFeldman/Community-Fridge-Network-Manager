package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.MenoresACargoIncorrectoException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class MenosACargoIncorrectoHandler implements IHandler {
    public void setHandle(Javalin app) {
        app.exception(MenoresACargoIncorrectoException.class, (e, ctx) -> {
            String nombre = ctx.formParam("nombre");
            String fechaNacimiento = ctx.formParam("fechaNacimiento");
            String domicilio = ctx.formParam("domicilio");
            String dni = ctx.formParam("dni");
            String numeroTarjeta = ctx.formParam("numeroTarjeta");

            ctx.status(401);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro de persona vulnerable");
            model.put("menoresIncorrecto", true);

            model.put("nombre", nombre);
            model.put("fechaNacimiento", fechaNacimiento);
            model.put("domicilio", domicilio);
            model.put("dni", dni);
            model.put("numeroTarjeta", numeroTarjeta);

            ctx.render("colaboraciones/registro-vulnerable.hbs", model);
        });
    }
}
