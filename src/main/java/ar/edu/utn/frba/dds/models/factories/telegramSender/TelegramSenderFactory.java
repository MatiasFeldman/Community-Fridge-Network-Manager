package ar.edu.utn.frba.dds.models.factories.telegramSender;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.ITelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;

public class TelegramSenderFactory {
    public static ITelegramSender create() {
        return new TelegramSender();
    }
}
