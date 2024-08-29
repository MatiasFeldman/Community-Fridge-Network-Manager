package ar.edu.utn.frba.dds.models.factories.telegramSender;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;

public class TelegramSenderFactory {
    public static TelegramSender create() {
        return new TelegramSender();
    }
}
