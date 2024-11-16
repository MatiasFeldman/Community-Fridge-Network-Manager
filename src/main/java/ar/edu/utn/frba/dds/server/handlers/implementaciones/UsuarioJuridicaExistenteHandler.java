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
            String razon_social = ctx.formParam("razon-social");
            String tipo = ctx.formParam("tipo") ;
            String rubro = ctx.formParam("rubro");
            String email = ctx.formParam("Mail");
            String telegram = ctx.formParam("Telegram");
            String whatsapp = ctx.formParam("Whatsapp");
            String direccionForm = ctx.formParam("direccion");
            String provinciaForm = ctx.formParam("provincia");

            ctx.status(400);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Juridica");
            model.put("usuarioExistente", e.getMessage());

            model.put("razon_social", razon_social);
            model.put("tipo", tipo);
            model.put("rubro", rubro);
            model.put("email", email);
            model.put("telegram", telegram);
            model.put("whatsapp", whatsapp);
            model.put("direccionForm", direccionForm);
            model.put("provinciaForm", provinciaForm);

            ctx.render("registro-usuario/registro-juridica.hbs",model);
        });
    }
}
