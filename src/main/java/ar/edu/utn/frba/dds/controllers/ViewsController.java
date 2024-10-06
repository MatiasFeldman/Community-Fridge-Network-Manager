package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ViewsController {

    public  static void landing(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Inicio");

        System.out.println(model.get("titulo"));
        ctx.render("landing.hbs", model);
    }

    public static void colaborar(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaborar");

        ctx.render("colaborar.hbs", model);
    }

    public static void formDonarDinero(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Donar dinero");

        ctx.render("colaboraciones/dinero.hbs", model);
    }

    public static void formDistribuirViandas(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Distribuir viandas");

        ctx.render("colaboraciones/distribucion-de-viandas.hbs", model);
    }

    public static void formDonarViandas(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Donar viandas");

        ctx.render("colaboraciones/donacion-de-viandas.hbs", model);
    }

    public static void formHeladeraACargo(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Hacerse cargo de heladera");


        ctx.render("colaboraciones/heladera-a-cargo.hbs", model);
    }

    public static void formRegistroPersonaVulnerable(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Registro de persona vulnerable");

        ctx.render("colaboraciones/registro-vulnerable.hbs", model);
    }

    public static void formRegistrarOferta(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Registro de oferta");

        ctx.render("colaboraciones/ofertar.hbs", model);
    }


    public static void formLogin(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login");

        ctx.render("login.hbs",model);
    }

    public static void formRegistro(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Registro");

        ctx.render("registro-usuario/registro-tipo.hbs", model);
    }

    public static void formFallaTecnica(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Reporte falla ténica");

        ctx.render("heladeras/fallas-tecnicas.hbs", model);
    }

    public static void cargaCsv(Context context){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Carga Csv");

        context.render("carga-csv.hbs", model);
    }

    public static void reportesHeladerasInicio(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Reportes de heladeras");

        context.render("reportes/main-reportes.hbs", model);
    }
}


