package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

public class ValidadorUsernames {
    public static Boolean existe(String username, String rol){
        return ServiceLocator.instanceOf(UsuariosRepository.class).existeUsername(username);
    }
}
