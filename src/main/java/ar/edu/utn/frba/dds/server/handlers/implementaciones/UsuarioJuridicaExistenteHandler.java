package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registro_usuario.UsuarioJuridicaExistenteException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class UsuarioJuridicaExistenteHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(UsuarioJuridicaExistenteException.class, (e, ctx) -> {
            ctx.status(400);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Juridica");
            model.put("usuarioExistente", e.getMessage());

            System.out.println("Usuario juridica existente detectado: " + e.getMessage());

            ctx.render("registro-usuario/registro-juridica.hbs",model);
        });
    }
}
