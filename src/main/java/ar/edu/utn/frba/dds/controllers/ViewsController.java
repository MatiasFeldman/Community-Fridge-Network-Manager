package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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

    public static void formProducto(Context ctx){
        ctx.render("colaboraciones/productos.hbs");
    }

    public static void formLogin(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login");

        ctx.render("login.hbs",model);
    }

    public static void formRegistro(Context ctx){
        ctx.render("registroUsuario/registro-tipo.hbs");
    }

    public static void formFallaTecnica(Context ctx){
        ctx.render("colaboraciones/fallas-tecnicas.hbs");
    }


}


