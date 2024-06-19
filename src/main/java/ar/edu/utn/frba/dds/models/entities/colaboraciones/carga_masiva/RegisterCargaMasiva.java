package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.IUsersRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.users.imp.UsersRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;

public class RegisterCargaMasiva {
    private HumanosRepository humanRepository;
    private UsersRepository usersRepository;

    public RegisterCargaMasiva(HumanosRepository humanRepository, UsersRepository usersRepository) {
        this.humanRepository = humanRepository;
        this.usersRepository = usersRepository;
    }

    public String registrarHumano(String[] line) throws IOException {
        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);

        Humano humano = new Humano();
        humano.generarAtributo(TipoAtributo.OBLIGATORIO, "Nombre", nombre);
        humano.generarAtributo(TipoAtributo.OBLIGATORIO, "Apellido", apellido);
        humano.generarContacto(new Contacto("Mail", mail));
        humano.generarAtributo(TipoAtributo.OPCIONAL,tipoDocumento, documento);

        ContribucionHumana contribucion = ContribucionHumanaFactory.create(formaColaboracion, cantidad);

        humano.agregarContribucion(contribucion);

        this.humanRepository.guardar(humano);

        String username = nombre.charAt(0) + apellido;
        String password = RandomStringUtils.randomAlphanumeric(16);
        this.usersRepository.guardar(new Usuario(username, password, new Rol("HUMANO")));

        return password;

    }

}
