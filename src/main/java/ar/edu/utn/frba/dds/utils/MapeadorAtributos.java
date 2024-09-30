package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

public class MapeadorAtributos {
    public static AtributoHumanoRespondido mapear(String valor, String nombre) {
        AtributosHumanoRepository atributosDisponibles = ServiceLocator.instanceOf(AtributosHumanoRepository.class);
        Atributo atributo = atributosDisponibles.buscarPorNombre(nombre).get();
        return new AtributoHumanoRespondido(atributo, valor);
    }
}
