package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import lombok.AllArgsConstructor;

import javax.mail.MessagingException;
import java.io.IOException;

@AllArgsConstructor
public class WhatsAppSendingStrategy implements SendingStrategy {
    private WhatsAppSender whatsAppSender;

    @Override
    public void enviarMensaje(Mensaje mensaje) throws IOException, MessagingException {
        whatsAppSender.enviarWhatsApp(mensaje.getDestinatario(), mensaje.getCuerpo());
    }
}
