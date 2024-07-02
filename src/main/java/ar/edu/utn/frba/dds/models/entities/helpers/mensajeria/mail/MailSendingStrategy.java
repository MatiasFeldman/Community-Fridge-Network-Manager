package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import lombok.AllArgsConstructor;

import javax.mail.MessagingException;
import java.io.IOException;

@AllArgsConstructor
public class MailSendingStrategy implements SendingStrategy {

    private IMailSender mailSender;

    @Override
    public void enviarMensaje(Mensaje mensaje) throws MessagingException {
        Mail mail = new Mail(mensaje.toString(), "Mensaje de la aplicación");
        mailSender.enviarMail(mensaje.getDestinatario(), mail);
    }
}
