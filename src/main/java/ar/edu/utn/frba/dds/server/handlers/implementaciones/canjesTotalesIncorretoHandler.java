package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.exceptions.donacionDinero.MontoInvalidoException;
import ar.edu.utn.frba.dds.exceptions.registro_oferta.canjesTotalesIncorretoException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import ar.edu.utn.frba.dds.models.repositories.rubros.RubrosRepository;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class canjesTotalesIncorretoHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(canjesTotalesIncorretoException.class, (e, context) -> {
            String nombreProducto = context.formParam("nombreProducto");
            String puntosNecesarios = context.formParam("puntosNecesarios");
            String rubro = context.formParam("rubro");


            Map<String, Object> model = new HashMap<>();
            List<Rubro> rubros;
            model.put("titulo", "Registro de oferta");
            rubros = ServiceLocator.instanceOf(RubrosRepository.class).buscarTodos();
            model.put("rubros", rubros);
            model.put("errorCanjesTotales", e.getMessage());

            model.put("nombreProducto", nombreProducto);
            model.put("puntosNecesarios", puntosNecesarios);
            model.put("rubro", rubro);

            RenderUtils.renderizar(context,"colaboraciones/ofertar.hbs", model);
        });
    }
}
