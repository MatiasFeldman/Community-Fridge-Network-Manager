package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.RegistroTarjetaInexistenteException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class RegistroTarjetaInexistenteHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(RegistroTarjetaInexistenteException.class, (e, context) -> {
            String nombre = context.formParam("nombre");
            String fechaNacimiento = context.formParam("fechaNacimiento");
            String domicilio = context.formParam("domicilio");
            String dni = context.formParam("dni");
            String menoresACargo = context.formParam("menoresACargo");

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Vulnerable");
            model.put("errorTarjeta", e.getMessage());

            model.put("nombre", nombre);
            model.put("fechaNacimiento", fechaNacimiento);
            model.put("domicilio", domicilio);
            model.put("dni", dni);
            model.put("menoresACargo", menoresACargo);

            context.render("colaboraciones/registro-vulnerable.hbs", model);
        });
    }
}
