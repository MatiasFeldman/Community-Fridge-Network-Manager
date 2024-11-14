package ar.edu.utn.frba.dds.server.handlers;


import ar.edu.utn.frba.dds.exceptions.InvalidContribucionException;
import ar.edu.utn.frba.dds.server.handlers.implementaciones.*;
import io.javalin.Javalin;

import java.util.Arrays;

public class AppHandlers {
    private IHandler[] handlers = new IHandler[]{
            new UsuarioIncorrectoHandler(),
            new ContraseniaIncorrectaHandler(),
            new AccessDeniedHandler(),
            new NoSesionIniciadaHandler(),
            new MenosACargoIncorrectoHandler(),
            new RegistroTarjetaInexistenteHandler(),
            new SolicitudesIncorrectasHandler(),
            new MontoInvalidoHandler(),
            new FechaNacimientoIncorrectaHandler(),
            new TemperaturaIncorrectaHandler(),
            new CapacidadIncorrectaHandler(),
            new CantidadViandasIncorrectaHandler(),
            new MismaHeladeraHanlder(),
            new APIIntegracionSinConexionHandler(),
            new PuntosInsuficientesHandler(),
            new ContraseniaJuridicaInseguraHanlder(),
            new UsuarioJuridicaExistenteHandler(),
            new DireccionJuridicaInexsistenteHandler(),
            new TarjetaRepetidaHandler(),
            new ContraseniaHumanoInseguraHandler(),
            new UsuarioHumanoExistenteHandler(),
            new DireccionIncorrectaHeladeraHandler(),
            new InvalidContribucionHandler(),
    };

    public static void applyHandlers(Javalin app) {
        Arrays.stream(new AppHandlers().handlers).toList().forEach(handler -> handler.setHandle(app));
    }
}
