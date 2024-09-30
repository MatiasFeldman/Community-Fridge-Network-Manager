package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

public class ValidadorUsernames {
    public static Boolean existe(String username, String rol){
        if (rol.equalsIgnoreCase("Humano")){
            return ServiceLocator.instanceOf(HumanosRepository.class).existeUsername(username);
        } else{
            return ServiceLocator.instanceOf(JuridicasRepository.class).existeUsername(username);
        }
    }
}
