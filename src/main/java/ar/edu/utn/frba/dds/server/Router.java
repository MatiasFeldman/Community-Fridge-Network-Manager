package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;


public class Router {
    public static void init(Javalin app){
        app.get("/heladeras", ctx -> ServiceLocator.instanceOf(HeladerasController.class).index(ctx));
    }
}
