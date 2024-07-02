package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.MailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.Mail;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.mailSender.MailSenderFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.users.imp.UsersRepository;
import com.opencsv.CSVReader;
import lombok.SneakyThrows;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class ConversorCSVReader implements ConversorCSV {
    private UsersRepository usersRespository;
    private ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository humanosRepository;
    private OfertasRepository ofertas;

    public ConversorCSVReader(UsersRepository repositorio, HumanosRepository humanosRepository, OfertasRepository ofertas) {
        this.usersRespository = repositorio;
        this.humanosRepository = humanosRepository;
        this.ofertas = ofertas;

    }

    @Override
    @SneakyThrows
    public void convertir(String path) {
        CSVReader reader = new CSVReader(new FileReader(path));
        ValidadorCargaMasiva validador = new ValidadorCargaMasiva();
        String[] line;
        while ((line = reader.readNext()) != null) {
            String nombre = line[2];
            String apellido = line[3];

            String fechaColaboracionString = line[5];

            if (!validador.validarLinea(line)) {
                continue;
            }

            this.registrarHumano(line);

        }
    }

    public void registrarHumano(String[] line) throws IOException, MessagingException {
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);
        String username = nombre.charAt(0) + apellido;

        if (usersRespository.buscarPorUsername(username).isEmpty()) {
            RegisterCargaMasiva registrador = new RegisterCargaMasiva(humanosRepository, usersRespository, ofertas);

            String password = registrador.registrarHumano(line);

            this.enviarMailBienvenida(mail, nombre, apellido, username, password);
        } else {
            Optional<Usuario> user = usersRespository.buscarPorUsername(username);
            UUID idUsuario = user.get().getId();
            Optional<Humano> humanoRegistrado = humanosRepository.buscarPorUUID(idUsuario);
            if (humanoRegistrado.isPresent()) {
                Humano human = humanoRegistrado.get();
                human.agregarContribucion(ContribucionHumanaFactory.create(formaColaboracion, cantidad));
            }

        }
    }

    public void enviarMailBienvenida(String mail, String nombre, String apellido, String username, String password)
            throws MessagingException {
        MailSender enviador = MailSenderFactory.create();
        enviador.enviarMail(mail,
                new Mail(this.cuerpoMail(nombre, apellido, username, password), "Colaboración pendiente"));
    }

    public String cuerpoMail(String nombre, String apellido, String username, String password) {
        return "Hola " + nombre + " " + apellido + ",\n\n"
                + "Muchas gracias por querer colaborar con nosotros. "
                + "Lamentablemente no pudimos encontrar tu usuario en nuestra base de datos. "
                + "Es por eso, que te creamos una cuenta para que confimers y/o actualices tus credenciales\n\n"
                + "Usuario: " + username + "\n"
                + "Contraseña: " + password + "\n\n"
                + "Saludos,\n"
                + "Equipo de colaboraciones";
    }

}
