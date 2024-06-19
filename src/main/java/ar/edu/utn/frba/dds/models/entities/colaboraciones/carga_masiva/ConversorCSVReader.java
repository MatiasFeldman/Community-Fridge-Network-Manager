package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.IMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.Mail;
import ar.edu.utn.frba.dds.models.factories.mailSender.MailSenderFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.users.imp.UsersRepository;
import com.opencsv.CSVReader;
import lombok.SneakyThrows;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.IOException;

public class ConversorCSVReader implements ConversorCSV {
    private UsersRepository usersRespository;
    private ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository humanosRepository;

    public ConversorCSVReader(UsersRepository repositorio, HumanosRepository humanosRepository) {
        this.usersRespository = repositorio;
        this.humanosRepository = humanosRepository;

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
/*
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaColaboracion = sdf.parse(fechaColaboracionString);
 */


        }
    }

    public void registrarHumano(String[] line) throws IOException, MessagingException {
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String username = nombre.charAt(0) + apellido;

        if (usersRespository.buscarPorUsername(username).isEmpty()) {
            RegisterCargaMasiva registrador = new RegisterCargaMasiva(humanosRepository, usersRespository);

            String password = registrador.registrarHumano(line);

            this.enviarMailBienvenida(mail, nombre, apellido, username, password);
        }
    }

    public void enviarMailBienvenida(String mail, String nombre, String apellido, String username, String password) throws MessagingException {
        IMailSender enviador = MailSenderFactory.create();
        enviador.enviarMail(mail, new Mail(this.cuerpoMail(nombre,apellido,username,password), "Colaboración pendiente"));
    }

    public String cuerpoMail(String nombre, String apellido, String username, String password){
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
