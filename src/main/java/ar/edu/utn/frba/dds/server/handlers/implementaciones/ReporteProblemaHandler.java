package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.ReportesProblemaException;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class ReporteProblemaHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(ReportesProblemaException.class, (e, context) -> {
            context.status(500);
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Error Reportes");
            context.render("/reportes/errorReportes.hbs", model);
        });
    }
}
