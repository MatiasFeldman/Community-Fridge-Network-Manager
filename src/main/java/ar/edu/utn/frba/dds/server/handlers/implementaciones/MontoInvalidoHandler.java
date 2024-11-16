package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.donacionDinero.MontoInvalidoException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class MontoInvalidoHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(MontoInvalidoException.class, (e, context) -> {
            String frecuencia = context.formParam("frecuencia");


            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Donar dinero");
            model.put("montoInvalido", true);
            model.put("frecuencia", frecuencia);

            context.render("colaboraciones/dinero.hbs", model);
        });
    }
}
