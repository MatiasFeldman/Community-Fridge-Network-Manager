package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registro_usuario.ContraseniaJuridicaInseguraException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class ContraseniaJuridicaInseguraHanlder implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(ContraseniaJuridicaInseguraException.class, (e, ctx) -> {
            ctx.status(400);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Juridica");
            model.put("contraseniaInsegura", e.getMessage());

            ctx.render("registro-usuario/registro-juridica.hbs",model);
        });
    }
}
