package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.DireccionJuridicaInexsistenteException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class DireccionJuridicaInexsistenteHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(DireccionJuridicaInexsistenteException.class, (e, ctx) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Juridica");
            model.put("direccionInvalida", "La dirección ingresada no es válida");

            ctx.render("registro-usuario/registro-juridica.hbs", model);
        });
    }
}
