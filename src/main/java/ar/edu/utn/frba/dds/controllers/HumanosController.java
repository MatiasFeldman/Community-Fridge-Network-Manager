package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.AtributoOutputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.http.Context;

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
}
