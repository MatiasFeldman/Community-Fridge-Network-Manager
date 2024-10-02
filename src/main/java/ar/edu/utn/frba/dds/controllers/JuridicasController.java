package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;

public class JuridicasController {
    public void formRegistro(Context context){
        context.render("registro-usuario/registro-juridica.hbs");
    }
}
