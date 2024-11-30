package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Canjes;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Contribucion;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.helpers.creador_usernames.UsernameGenerator;
import ar.edu.utn.frba.dds.models.entities.personas.*;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tipo_documento;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.personas.HumanoFactory;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.seguridad.GeneradorDeContrasenias;
import ar.edu.utn.frba.dds.utils.seguridad.HashPassword;

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

    public UsuarioConPassword registrarHumano(String[] line) throws IOException {
        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);

        Atributo nombreAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Nombre").get();
        Atributo apellidoAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Apellido").get();
        Atributo nacimientoAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Nacimiento").get();
        Atributo mailAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Mail").get();
        Atributo direccionAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Direccion").get();
        Atributo provinciaAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Provincia").get();
        Atributo wppAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("WhatsApp").get();
        Atributo telegramAtributo = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Telegram").get();

        ArrayList<AtributoHumanoRespondido> atributosObligatorios = new ArrayList<>(Arrays.asList(AtributoHumanoRespondido.create(nombreAtributo, nombre),
                AtributoHumanoRespondido.create(apellidoAtributo, apellido),
                AtributoHumanoRespondido.create(mailAtributo, mail)));

        ArrayList<AtributoHumanoRespondido> atributosOpcionales = new ArrayList<>(Arrays.asList(AtributoHumanoRespondido.create(nacimientoAtributo, ""),
                AtributoHumanoRespondido.create(direccionAtributo, ""),
                AtributoHumanoRespondido.create(provinciaAtributo, ""),
                AtributoHumanoRespondido.create(wppAtributo, ""),
                AtributoHumanoRespondido.create(telegramAtributo, "")));

        UsuarioConPassword userCreadoConPass = this.crearUsuarioHumanoPass(nombre, apellido);
        Usuario userCreado = userCreadoConPass.getUsuario();

        ColaboradorHumano creado = this.crearHumano(atributosObligatorios, atributosOpcionales,new ArrayList<>() ,userCreado);

        creado.setTipoDocumento(Tipo_documento.valueOf(tipoDocumento));
        creado.setDocumento(documento);

        this.agregarContribucion(creado, formaColaboracion, cantidad);

        this.guardarEnRepositorios(creado);

        return userCreadoConPass;

    }

    public UsuarioConPassword crearUsuarioHumanoPass(String nombre, String apellido) throws IOException {
        UsernameGenerator usernameGenerator = new UsernameGenerator(humanRepository);
        String username = usernameGenerator.generateUsername(nombre, apellido);
        String password = GeneradorDeContrasenias.generateRandomString(16);
        // hashear password

        System.out.println("Usuario creado: " + username + " " + password);
        Usuario usuario = new Usuario(username, ServiceLocator.instanceOf(HashPassword.class).hashPassword(password), new ArrayList<>(List.of(TipoRol.HUMANO)));
        return new UsuarioConPassword(usuario, password);
    }

    public Usuario crearUsuarioHumano(String nombre, String apellido) throws IOException {
        UsernameGenerator usernameGenerator = new UsernameGenerator(humanRepository);
        String username = usernameGenerator.generateUsername(nombre, apellido);
        String password = GeneradorDeContrasenias.generateRandomString(16);
        // hashear password

        System.out.println("Usuario creado: " + username + " " + password);
        return new Usuario(username, ServiceLocator.instanceOf(HashPassword.class).hashPassword(password), new ArrayList<>(List.of(TipoRol.HUMANO)));
    }

    public ColaboradorHumano crearHumano(ArrayList<AtributoHumanoRespondido> obligatorios, ArrayList<AtributoHumanoRespondido> opcionales, ArrayList<Canjes> canjes , Usuario userCreado){
        HumanoInputDTO dto = new HumanoInputDTO(obligatorios,List.of("Mail","WhatsApp","Telegram") ,opcionales , userCreado, null);
        return HumanoFactory.crear(dto);
    }

    public void agregarContribucion(ColaboradorHumano colaboradorHumano, String formaColaboracion, Integer cantidad){
        Contribucion contribucion = ContribucionHumanaFactory.createForCargaMasiva(formaColaboracion, cantidad, colaboradorHumano);
    }

    public void guardarEnRepositorios(ColaboradorHumano colaboradorHumano){

        this.humanRepository.guardar(colaboradorHumano);

    }

}
