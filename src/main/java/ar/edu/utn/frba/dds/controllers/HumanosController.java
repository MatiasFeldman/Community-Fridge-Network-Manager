package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.AtributoOutputDTO;
import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.server.Server;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.MapeadorAtributos;
import ar.edu.utn.frba.dds.utils.ValidadorUsernames;
import ar.edu.utn.frba.dds.utils.seguridad.ValidadorDeContrasenias;
import com.fasterxml.jackson.databind.JsonNode;
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

        Map<String, Object> model = new HashMap<>();
        model.put("campos", dtos);

        context.render("registro-usuario/registro-humano.hbs", model);
        System.out.println("Se renderizo el formulario de registro de humano");
    }

    public void camposFormHumano(Context context){
        List<Atributo> atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarTodas();
        List<AtributoOutputDTO> dtos = new ArrayList<>();

        atributos.forEach(a -> dtos.add(AtributoOutputDTO.of(a)));

        Map<String, Object> model = new HashMap<>();
        model.put("campos", dtos);

        context.render("registro-usuario/modif-registro-humano.hbs", model);
    }

    public void save(Context context){



        String body = context.body();

        JsonNode json = ConversorJSON.convertir(body);

        System.out.println(body);

        String password = json.get("password").asText();

        ValidadorDeContrasenias validador = ServiceLocator.instanceOf(ValidadorDeContrasenias.class);

        if (!validador.esValida(password)){
            context.status(HttpStatus.BAD_REQUEST);
            String motivoInvalidez = validador.condicionQueNoCumple(password).get().getMensaje();

            Map<String, String> response = new HashMap<>();
            response.put("motivo", motivoInvalidez);

            context.json(response);
            return;
        }

        String username = json.get("user").asText();

        if(ValidadorUsernames.existe(username, "Humano")){
            context.status(HttpStatus.BAD_REQUEST);

            Map<String, String> response = new HashMap<>();
            response.put("motivo", "El nombre de usuario ya existe");

            context.json(response);
            return;
        }

        String nombre = json.get("nombre").asText();
        String apellido = json.get("apellido").asText();
        String email = json.get("email").asText();
        String telegram = json.get("telegram").asText();
        String whatsapp = json.get("wpp").asText();
        String direccionForm = json.get("direccion").asText();
        String provinciaForm = json.get("provincia").asText();
        String nacimiento = json.get("nacimiento").asText();

        AtributoHumanoRespondido atributoNombre = MapeadorAtributos.mapear(nombre, "nombre");
        AtributoHumanoRespondido atributoApellido = MapeadorAtributos.mapear(apellido, "apellido");
        AtributoHumanoRespondido atributoEmail = MapeadorAtributos.mapear(email, "mail");
        AtributoHumanoRespondido atributoTelegram = MapeadorAtributos.mapear(telegram, "telegram");
        AtributoHumanoRespondido atributoWhatsapp = MapeadorAtributos.mapear(whatsapp, "whatsapp");
        AtributoHumanoRespondido atributoNacimiento = MapeadorAtributos.mapear(nacimiento, "nacimiento");

        HumanoInputDTO dto = HumanoInputDTO.create(username, password, atributoNombre, atributoApellido, atributoEmail, atributoTelegram, atributoWhatsapp, atributoNacimiento);
        if (!direccionForm.isEmpty()){
            Direccion direccion = DireccionFactory.create(new DireccionInputDTO(direccionForm, provinciaForm));
            dto.setDireccion(direccion);
        }

        ColaboradorHumano colaborador = ColaboradorHumano.create(dto);
        ServiceLocator.instanceOf(HumanosRepository.class).guardar(colaborador);
        ServiceLocator.instanceOf(UsuariosRepository.class).guardar(colaborador.getUser());
    }

}
