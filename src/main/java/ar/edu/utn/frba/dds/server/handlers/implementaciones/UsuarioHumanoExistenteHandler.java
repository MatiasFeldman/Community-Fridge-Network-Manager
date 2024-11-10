package ar.edu.utn.frba.dds.server.handlers.implementaciones;

import ar.edu.utn.frba.dds.dtos.AtributoOutputDTO;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.ContraseniaHumanoInseguraException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.UsuarioHumanoExistenteException;
import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.server.handlers.IHandler;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioHumanoExistenteHandler implements IHandler {

    @Override
    public void setHandle(Javalin app) {
        app.exception(UsuarioHumanoExistenteException.class, (e, ctx) -> {
            ctx.status(400);
            List<Atributo> atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarTodas();
            List<AtributoOutputDTO> dtos = new ArrayList<>();

            atributos.forEach(a -> dtos.add(AtributoOutputDTO.of(a)));

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Registro Humano");
            model.put("campos", dtos);
            model.put("usuarioExistente", e.getMessage());

            ctx.render("registro-usuario/registro-humano.hbs",model);
        });
    }
}
