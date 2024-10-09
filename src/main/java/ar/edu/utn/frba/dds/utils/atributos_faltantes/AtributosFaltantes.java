package ar.edu.utn.frba.dds.utils.atributos_faltantes;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class AtributosFaltantes {
    public static List<AtributoHumanoRespondido> todosLosAtributosDe(List<AtributoHumanoRespondido> atributosRespondidos){
        List<Atributo> atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarTodas();
        List<AtributoHumanoRespondido> atributosADevolver = new ArrayList<>(atributosRespondidos);

        for (Atributo a : atributos){
            if (!atributosRespondidos.stream().anyMatch(atributo -> atributo.getNombreAtributo().equals(a.getNombre()))){
                AtributoHumanoRespondido noRespondido = new AtributoHumanoRespondido(a, "");
                atributosADevolver.add(noRespondido);
            }
        }

        return atributosADevolver;
    }
}
