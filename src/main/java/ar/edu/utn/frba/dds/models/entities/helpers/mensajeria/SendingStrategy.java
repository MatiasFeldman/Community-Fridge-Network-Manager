package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria;

import javax.mail.MessagingException;
import java.io.IOException;

public interface SendingStrategy {
    void enviarMensaje(Mensaje mensaje) throws IOException, MessagingException;
}
