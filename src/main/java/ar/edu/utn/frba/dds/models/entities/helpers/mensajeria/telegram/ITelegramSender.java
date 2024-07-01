package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram;

import java.io.IOException;

public interface ITelegramSender {
    void enviarTelegram(String destinatario, String mensaje) throws IOException;
}
