package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;

public class ViewsController {

    public  static void landing(Context ctx){
        ctx.render("landing.hbs");
    }

    public static void colaborar(Context ctx){
        ctx.render("colaborar.hbs");
    }

    public static void formDonarDinero(Context ctx){
        ctx.render("colaboraciones/dinero.hbs");
    }

    public static void formDistribuirViandas(Context ctx){
        ctx.render("colaboraciones/distribucion-de-viandas.hbs");
    }

    public static void formDonarViandas(Context ctx){
        ctx.render("colaboraciones/donacion-de-viandas.hbs");
    }

    public static void formHeladeraACargo(Context ctx){
        ctx.render("colaboraciones/heladera-a-cargo.hbs");
    }

    public static void formRegistroPersonaVulnerable(Context ctx){
        ctx.render("colaboraciones/registro-vulnerable.hbs");
    }

    public static void formRegistrarOferta(Context ctx){
        ctx.render("colaboraciones/ofertar.hbs");
    }
}
