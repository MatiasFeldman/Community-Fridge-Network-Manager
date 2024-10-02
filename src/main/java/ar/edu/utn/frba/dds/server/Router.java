package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.controllers.HumanosController;
import ar.edu.utn.frba.dds.controllers.JuridicasController;
import ar.edu.utn.frba.dds.controllers.ViewsController;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;


public class Router {
    public static void init(Javalin app) {
        app.get("/", ViewsController::landing);

        app.get("/colaborar", ViewsController::colaborar);

        app.get("/colaborar/donar-dinero", ViewsController::formDonarDinero);

        app.get("/colaborar/distribuir-viandas", ViewsController::formDistribuirViandas);

        app.get("/colaborar/donar-viandas", ViewsController::formDonarViandas);

        app.get("/colaborar/heladera-a-cargo", ViewsController::formHeladeraACargo);

        app.get("/colaborar/registro-persona-vulnerable", ViewsController::formRegistroPersonaVulnerable);

        app.get("/colaborar/ofertar", ViewsController::formRegistrarOferta);

        app.get("/heladeras/suscribirse", ctx -> ServiceLocator.instanceOf(HeladerasController.class).suscribirseAHeladeras(ctx));

        app.get("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).formRegistroHumano(ctx));

        app.post("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).save(ctx));

        app.get("/registro/juridica", ctx -> ServiceLocator.instanceOf(JuridicasController.class).formRegistro(ctx));

        app.get("/heladeras/reportar-falla-tecnica", ViewsController::formFallaTecnica);

        app.get("/login", ViewsController::formLogin);

        app.get("/registro", ViewsController::formRegistro);

        app.get("/heladeras", ctx -> ServiceLocator.instanceOf(HeladerasController.class).index(ctx));

        app.get("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
        app.post("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
    }
}
