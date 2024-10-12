package ar.edu.utn.frba.dds.server.handlers;


import ar.edu.utn.frba.dds.server.handlers.implementaciones.*;
import io.javalin.Javalin;

import java.util.Arrays;

public class AppHandlers {
    private IHandler[] handlers = new IHandler[]{
            new UsuarioIncorrectoHandler(),
            new ContraseniaIncorrectaHandler(),
            new AccessDeniedHandler(),
            new NoSesionIniciadaHandler(),
            new RegistroPersonaVulnerableIncompletoHandler(),
            new RegistroTarjetaInexistenteHandler()
    };

    public static void applyHandlers(Javalin app) {
        Arrays.stream(new AppHandlers().handlers).toList().forEach(handler -> handler.setHandle(app));
    }
}
