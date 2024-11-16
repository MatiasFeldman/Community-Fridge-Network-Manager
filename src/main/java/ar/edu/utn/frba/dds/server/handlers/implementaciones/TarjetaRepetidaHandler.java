package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registro_usuario.TarjetaRepetidaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class TarjetaRepetidaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(TarjetaRepetidaException.class, (e, ctx) -> {
            String nombre = ctx.formParam("nombre");
            String fechaNacimiento = ctx.formParam("fechaNacimiento");
            String domicilio = ctx.formParam("domicilio");
            String dni = ctx.formParam("dni");
            String menoresACargo = ctx.formParam("menoresACargo");

            ctx.status(400);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Vulnerable");
            model.put("errorTarjeta", e.getMessage());

            model.put("nombre", nombre);
            model.put("fechaNacimiento", fechaNacimiento);
            model.put("domicilio", domicilio);
            model.put("dni", dni);
            model.put("menoresACargo", menoresACargo);

            ctx.render("colaboraciones/registro-vulnerable.hbs", model);
        });
    }
}
