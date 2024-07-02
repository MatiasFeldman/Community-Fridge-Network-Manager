package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.personas.*;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.personas.HumanoFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.users.imp.UsersRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RegisterCargaMasiva {
    private HumanosRepository humanRepository;
    private UsersRepository usersRepository;
    private OfertasRepository ofertas;

    public RegisterCargaMasiva(HumanosRepository humanRepository, UsersRepository usersRepository, OfertasRepository ofertas) {
        this.humanRepository = humanRepository;
        this.usersRepository = usersRepository;
        this.ofertas = ofertas;
    }

    public String registrarHumano(String[] line) throws IOException {
        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);
        UUID id = UUID.randomUUID();

        ArrayList<AtributoHumano> atributosObligatorios = new ArrayList<>(Arrays.asList(new AtributoHumano("Nombre", nombre), new AtributoHumano("Apellido", apellido)));

        ArrayList<Contacto> mediosDeContacto = new ArrayList<>(List.of(new Contacto("Mail", mail)));

        ArrayList<AtributoHumano> atributosOpcionales = new ArrayList<>(List.of(new AtributoHumano(tipoDocumento, documento)));

        Humano creado = this.crearHumano(atributosObligatorios, atributosOpcionales, mediosDeContacto, id);

        this.agregarContribucion(creado, formaColaboracion, cantidad);

        String pass = this.guardarEnRepositorios(creado, nombre, apellido);

        return pass;

    }

    public Humano crearHumano(ArrayList<AtributoHumano> obligatorios, ArrayList<AtributoHumano> opcionales, ArrayList<Contacto> contactos, UUID id){
        HumanoInputDTO dto = new HumanoInputDTO(obligatorios, contactos, opcionales, new ArrayList<>(), ofertas , id);

        return HumanoFactory.crear(dto);
    }

    public void agregarContribucion(Humano humano, String formaColaboracion, Integer cantidad){
        ContribucionHumana contribucion = ContribucionHumanaFactory.create(formaColaboracion, cantidad);
        humano.agregarContribucion(contribucion);
    }

    public String guardarEnRepositorios(Humano humano, String nombre, String apellido) throws IOException {

        String username = nombre.charAt(0) + apellido;
        String password = RandomStringUtils.randomAlphanumeric(16);

        this.usersRepository.guardar(new Usuario(username, password, humano.getIdUsuario(), new Rol("HUMANO")));
        this.humanRepository.guardar(humano);

        return password;
    }

}
