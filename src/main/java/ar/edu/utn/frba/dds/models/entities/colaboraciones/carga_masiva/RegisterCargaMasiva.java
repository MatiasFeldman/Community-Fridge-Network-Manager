package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Contribucion;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.helpers.creador_usernames.UsernameGenerator;
import ar.edu.utn.frba.dds.models.entities.personas.*;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.personas.HumanoFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.utils.seguridad.GeneradorDeContrasenias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterCargaMasiva {
    private HumanosRepository humanRepository;
    private OfertasRepository ofertas;

    public RegisterCargaMasiva(HumanosRepository humanRepository, OfertasRepository ofertas) {
        this.humanRepository = humanRepository;
        this.ofertas = ofertas;
    }

    public Usuario registrarHumano(String[] line) throws IOException {
        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);

        ArrayList<AtributoHumanoRespondido> atributosObligatorios = new ArrayList<>(Arrays.asList(AtributoHumanoRespondido.create("Nombre", nombre), AtributoHumanoRespondido.create("Apellido", apellido)));

        ArrayList<Contacto> mediosDeContacto = new ArrayList<>(List.of(new Contacto("Mail", mail)));

        ArrayList<AtributoHumanoRespondido> atributosOpcionales = new ArrayList<>(List.of(AtributoHumanoRespondido.create(tipoDocumento, documento)));

        Usuario userCreado = this.crearUsuarioHumano(nombre, apellido);

        Humano creado = this.crearHumano(atributosObligatorios, atributosOpcionales, mediosDeContacto, userCreado);

        this.agregarContribucion(creado, formaColaboracion, cantidad);

        this.guardarEnRepositorios(creado);

        return creado.getUser();

    }

    public Usuario crearUsuarioHumano(String nombre, String apellido) throws IOException {
        UsernameGenerator usernameGenerator = new UsernameGenerator(humanRepository);
        String username = usernameGenerator.generateUsername(nombre, apellido);
        String password = GeneradorDeContrasenias.generateRandomString(16);
        return new Usuario(username, password, new ArrayList<>(List.of(new Rol("HUMANO"))));
    }

    public Humano crearHumano(ArrayList<AtributoHumanoRespondido> obligatorios, ArrayList<AtributoHumanoRespondido> opcionales, ArrayList<Contacto> contactos, Usuario userCreado){
        HumanoInputDTO dto = new HumanoInputDTO(obligatorios, contactos, opcionales, new ArrayList<>(), ofertas , userCreado);
        return HumanoFactory.crear(dto);
    }

    public void agregarContribucion(Humano humano, String formaColaboracion, Integer cantidad){
        Contribucion contribucion = ContribucionHumanaFactory.createForCargaMasiva(formaColaboracion, cantidad);
        humano.agregarContribucion(contribucion);
    }

    public void guardarEnRepositorios(Humano humano){

        this.humanRepository.guardar(humano);

    }

}
