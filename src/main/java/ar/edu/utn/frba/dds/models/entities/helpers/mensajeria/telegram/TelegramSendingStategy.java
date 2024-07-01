package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import lombok.AllArgsConstructor;

import javax.mail.MessagingException;
import java.io.IOException;

@AllArgsConstructor
public class TelegramSendingStategy implements SendingStrategy {
    private ITelegramSender telegramSender;

    @Override
    public void enviarMensaje(Mensaje mensaje) throws IOException, MessagingException {
        telegramSender.enviarTelegram(mensaje.getDestinatario(), mensaje.getCuerpo());
    }
}