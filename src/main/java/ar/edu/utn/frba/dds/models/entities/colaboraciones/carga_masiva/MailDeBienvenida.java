package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.Mail;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSender;
import ar.edu.utn.frba.dds.models.factories.mailSender.MailSenderFactory;

import javax.mail.MessagingException;

public class MailDeBienvenida {
    public static String cuerpoMail(String nombre, String apellido, String username, String password) {
        return "Hola " + nombre + " " + apellido + ",\n\n"
                + "Muchas gracias por querer colaborar con nosotros. "
                + "Lamentablemente no pudimos encontrar tu usuario en nuestra base de datos. "
                + "Es por eso, que te creamos una cuenta para que confimers y/o actualices tus credenciales\n\n"
                + "Usuario: " + username + "\n"
                + "Contraseña: " + password + "\n\n"
                + "Saludos,\n"
                + "Equipo de colaboraciones";
    }

    public static void enviarMailBienvenida(String mail, String nombre, String apellido, String username, String password)
            throws MessagingException {
        MailSender enviador = MailSenderFactory.create();
        enviador.enviarMail(mail,
                new Mail(cuerpoMail(nombre, apellido, username, password), "Colaboración pendiente"));
    }
}
