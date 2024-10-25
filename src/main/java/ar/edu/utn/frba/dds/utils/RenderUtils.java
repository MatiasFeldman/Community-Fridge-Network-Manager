package ar.edu.utn.frba.dds.utils;


import io.javalin.http.Context;
import java.util.Map;

public class RenderUtils {
    public static void renderizar(Context ctx, String template, Map<String, Object> model) {
        // Agregar la información del usuario al modelo
        String nombreUsuario = ctx.sessionAttribute("nombreUsuario");
        String rolUsuario = ctx.sessionAttribute("rolUsuario");
        String fotoUsuario = ctx.sessionAttribute("fotoUsuario");

        model.put("nombreUsuario", nombreUsuario);
        model.put("rolUsuario", rolUsuario);
        model.put("fotoUsuario", fotoUsuario);

        // Renderizar el template con el modelo
        ctx.render(template, model);
    }
}
