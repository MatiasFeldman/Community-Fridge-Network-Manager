package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.AtributoOutputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.MapeadorAtributos;
import ar.edu.utn.frba.dds.utils.StringToDireccion;
import ar.edu.utn.frba.dds.utils.ValidadorUsernames;
import ar.edu.utn.frba.dds.utils.seguridad.ValidadorDeContrasenias;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumanosController {
    public Object crear(Object solicitud){
        HumanoInputDTO dto = (HumanoInputDTO) solicitud;

        return ColaboradorHumano.create(dto);
    }

    public void formRegistroHumano(Context context){
        List<Atributo> atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarTodas();
        List<AtributoOutputDTO> dtos = new ArrayList<>();

        atributos.forEach(a -> dtos.add(AtributoOutputDTO.of(a)));
        System.out.println(dtos.get(0).getNombre());

        Map<String, Object> model = new HashMap<>();
        model.put("campos", dtos);

        context.render("registro-usuario/registro-humano.hbs", model);
    }

    public void save(Context context){

        String password = context.formParam("password");

        ValidadorDeContrasenias validador = ServiceLocator.instanceOf(ValidadorDeContrasenias.class);

        if (!validador.esValida(password)){
            context.status(HttpStatus.BAD_REQUEST);
            String motivoInvalidez = validador.condicionQueNoCumple(password).get().getMensaje();

            Map<String, String> response = new HashMap<>();
            response.put("motivo", motivoInvalidez);

            context.json(response);
            return;
        }

        String username = context.formParam("user");

        if(ValidadorUsernames.existe(username, "Humano")){
            context.status(HttpStatus.BAD_REQUEST);

            Map<String, String> response = new HashMap<>();
            response.put("motivo", "El nombre de usuario ya existe");

            context.json(response);
            return;
        }

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String email = context.formParam("email");
        String telegram = context.formParam("telegram");
        String whatsapp = context.formParam("whatsapp");
        String direccionForm = context.formParam("direccion");
        String nacimiento = context.formParam("nacimiento");

        AtributoHumanoRespondido atributoNombre = MapeadorAtributos.mapear(nombre, "nombre");
        AtributoHumanoRespondido atributoApellido = MapeadorAtributos.mapear(apellido, "apellido");
        AtributoHumanoRespondido atributoEmail = MapeadorAtributos.mapear(email, "email");
        AtributoHumanoRespondido atributoTelegram = MapeadorAtributos.mapear(telegram, "telegram");
        AtributoHumanoRespondido atributoWhatsapp = MapeadorAtributos.mapear(whatsapp, "whatsapp");
        AtributoHumanoRespondido atributoNacimiento = MapeadorAtributos.mapear(nacimiento, "nacimiento");

        HumanoInputDTO dto = HumanoInputDTO.create(username, password, atributoNombre, atributoApellido, atributoEmail, atributoTelegram, atributoWhatsapp, atributoNacimiento);
        if (direccionForm != null){
            Direccion direccion = StringToDireccion.convert(direccionForm);
            dto.setDireccion(direccion);
        }
        ColaboradorHumano colaborador = ColaboradorHumano.create(dto);
        ServiceLocator.instanceOf(HumanosRepository.class).guardar(colaborador);
    }
}
