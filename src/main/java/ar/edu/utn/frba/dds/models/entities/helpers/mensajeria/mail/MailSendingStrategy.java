package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.factories.mailSender.MailSenderFactory;
import lombok.AllArgsConstructor;

import javax.mail.MessagingException;
import java.io.IOException;

@AllArgsConstructor
public class MailSendingStrategy implements SendingStrategy {

    private MailSender mailSender;

    @Override
    public void enviarMensaje(Mensaje mensaje) throws MessagingException {
        //Mail mail = new Mail(mensaje.getDestinatario(), "Mensaje de la aplicación");
        //mailSender.enviarMail(mensaje.getCuerpo(), mail);
        mailSender.enviarMail(mensaje.getCuerpo(), new Mail(mensaje.getDestinatario(), "Mensaje de la aplicación"));
    }
}
