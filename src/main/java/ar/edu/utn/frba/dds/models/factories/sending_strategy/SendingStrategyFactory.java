package ar.edu.utn.frba.dds.models.factories.sending_strategy;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSendingStategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSendingStrategy;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

public class SendingStrategyFactory {

    public static SendingStrategy create(String strategy){
        return switch (strategy) {
            case "TELEGRAM" -> new TelegramSendingStategy(ServiceLocator.getTelegramSender());
            case "EMAIL" -> new MailSendingStrategy(ServiceLocator.getMimeMailSender());
            case "WHATSAPP" -> new WhatsAppSendingStrategy(ServiceLocator.getWhatsAppSender());
            default -> new MailSendingStrategy(new MimeMailSender());
        };
    }
}
