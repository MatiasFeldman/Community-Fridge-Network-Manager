package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.dtos.juridico.JuridicoInputDTO;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.Tipo;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.MapeadorAtributos;
import ar.edu.utn.frba.dds.utils.ValidadorUsernames;
import ar.edu.utn.frba.dds.utils.seguridad.ValidadorDeContrasenias;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JuridicasController {
    public void create(Context context){
        context.render("registro-usuario/registro-juridica.hbs");
    }
    public void save(Context ctx){

        String password = ctx.formParam("password");

        ValidadorDeContrasenias validador = ServiceLocator.instanceOf(ValidadorDeContrasenias.class);

        if (!validador.esValida(password)){
            ctx.status(HttpStatus.BAD_REQUEST);
            String motivoInvalidez = validador.condicionQueNoCumple(password).get().getMensaje();

            Map<String, String> response = new HashMap<>();
            response.put("motivo", motivoInvalidez);

            ctx.json(response);
            return;
        }

        if(ValidadorUsernames.existe(ctx.formParam("user"), "Juridico")){
            ctx.status(HttpStatus.BAD_REQUEST);

            Map<String, String> response = new HashMap<>();
            response.put("motivo", "El nombre de usuario ya existe");

            ctx.json(response);
            return;
        }

        String username = ctx.formParam("user");
        String razon_social = ctx.formParam("razon-social");
        String tipo = ctx.formParam("tipo") ;
        String rubro = ctx.formParam("rubro");
        String email = ctx.formParam("email");
        String telegram = ctx.formParam("telegram");
        String whatsapp = ctx.formParam("whatsapp");

        List<Contacto> medioContacto = new ArrayList<>();

        if (email != null && !email.isEmpty()) {
            medioContacto.add(Contacto.of("EMAIL", email));
        }

        if (telegram != null && !telegram.isEmpty()) {
            medioContacto.add(Contacto.of("TELEGRAM", telegram));
        }

        if (whatsapp != null && !whatsapp.isEmpty()) {
            medioContacto.add(Contacto.of("WHATSAPP", whatsapp));
        }

        String direccionForm = ctx.formParam("direccion");
        String provinciaForm = ctx.formParam("provincia");

        //TODO
        //fijarnos de poner obligatorio en el front que si pone direccion tiene que poner provincia
        JuridicoInputDTO dto = new JuridicoInputDTO(username,password,razon_social, tipo, rubro,medioContacto,direccionForm,provinciaForm );

        Juridica juridica = Juridica.create(dto);

        ServiceLocator.instanceOf(JuridicasRepository.class).guardar(juridica);


    }
}
