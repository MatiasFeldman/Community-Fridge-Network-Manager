package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;


public class Router {
    public static void init(Javalin app){
        app.get("/", ctx -> ctx.render("landing.hbs"));
        app.get("/colaborar", ctx -> ctx.render("colaborar.hbs"));

        app.get("/colaborar/donar-dinero", ctx -> ctx.render("colaboraciones/dinero.hbs"));

        app.get("/colaborar/distribuir-viandas", ctx -> ctx.render("colaboraciones/distribucion-de-viandas.hbs"));

        app.get("/colaborar/donar-viandas", ctx -> ctx.render("colaboraciones/donacion-de-viandas.hbs"));

        app.get("/colaborar/heladera-a-cargo", ctx -> ctx.render("colaboraciones/heladera-a-cargo.hbs"));



        app.get("/heladeras", ctx -> ServiceLocator.instanceOf(HeladerasController.class).index(ctx));

        app.get("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
        app.post("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
    }
}
