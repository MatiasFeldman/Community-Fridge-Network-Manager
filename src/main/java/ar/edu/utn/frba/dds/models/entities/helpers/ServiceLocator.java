package ar.edu.utn.frba.dds.models.entities.helpers;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import lombok.Setter;

public class ServiceLocator {


    private MimeMailSender mimeMailSender;
    private TelegramSender telegramSender;

    private static ServiceLocator instance = null;

    public ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public static MimeMailSender getMimeMailSender() {
        return instance.mimeMailSender;
    }

    public static TelegramSender getTelegramSender() {
        return instance.telegramSender;
    }

    public static void setMimeMailSender(MimeMailSender mimeMailSender) {
        instance.mimeMailSender = mimeMailSender;
    }

    public static void setTelegramSender(TelegramSender telegramSender) {
        instance.telegramSender = telegramSender;
    }

}
