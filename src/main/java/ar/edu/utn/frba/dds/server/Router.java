package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;


public class Router {
    public static void init(Javalin app){
        app.get("/", ctx -> ctx.render("landing.hbs"));
        app.get("/colaborar", ctx -> ctx.render("colaborar.hbs"));


        app.get("/heladeras", ctx -> ServiceLocator.instanceOf(HeladerasController.class).index(ctx));

        app.get("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
        app.post("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
    }
}
