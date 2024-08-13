package ar.edu.utn.frba.dds.models.entities.helpers.creador_usernames;

import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import org.apache.commons.lang3.RandomStringUtils;


public class UsernameGenerator {
    private HumanosRepository humanosRepository;

    public UsernameGenerator(HumanosRepository humanosRepository) {
        this.humanosRepository = humanosRepository;
    }
    public String generateUsername(String nombre, String apellido) {
        String apellido_junto = apellido.replaceAll("\\s+", "");
        String primera_letra_nombre = String.valueOf(nombre.charAt(0));
        String username = primera_letra_nombre + apellido_junto + RandomStringUtils.randomAlphanumeric(10);
        if (!humanosRepository.existeUsername(username)) {
            return username;
        } else{
            return generateUsername(nombre, apellido);
        }
    }
}
