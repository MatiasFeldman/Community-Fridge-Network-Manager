package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.registroHeladera.TemperaturaIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.tecnicoDocumentoIncorrectoException;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tipo_documento;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import io.javalin.Javalin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class tecnicoDocumentoIncorrectoHandler implements IHandler {
    public void setHandle(Javalin app) {
        app.exception(tecnicoDocumentoIncorrectoException.class, (e, ctx) -> {
            List<Map<String, Object>> tipoDocConNumeros = Arrays.stream(Tipo_documento.values())
                    .map(tipo_documento -> {
                        Map<String, Object> tipoDocMap = new HashMap<>();
                        tipoDocMap.put("nombre", tipo_documento.name());
                        tipoDocMap.put("valor", tipo_documento.ordinal());
                        return tipoDocMap;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> model = new HashMap<>();
            model.put("tipos",tipoDocConNumeros);

            String password = ctx.formParam("password");
            String username = ctx.formParam("user");
            String direccion = ctx.formParam("direccion");
            String provincia = ctx.formParam("provincia");
            String nombre = ctx.formParam("nombre");
            String apellido = ctx.formParam("apellido");
            String tipo_documento = ctx.formParam("tipo_documento");
            String radio = ctx.formParam("radio");
            String nroCUIL = ctx.formParam("nroCUIL");
            String email = ctx.formParam("Mail");
            String telegram = ctx.formParam("Telegram");
            String whatsapp = ctx.formParam("Whatsapp");

            model.put("password", password);
            model.put("user", username);
            model.put("direccion", direccion);
            model.put("provincia", provincia);
            model.put("nombre", nombre);
            model.put("apellido", apellido);
            model.put("radio", radio);
            model.put("nroCUIL", nroCUIL);
            model.put("Mail", email);
            model.put("Telegram", telegram);
            model.put("Whatsapp", whatsapp);
            model.put("tipo_documento", tipo_documento);

            model.put("errorDocumento", e.getMessage());

            RenderUtils.renderizar(ctx,"registro-usuario/registro-tecnico.hbs",model);
        });
    }
}
