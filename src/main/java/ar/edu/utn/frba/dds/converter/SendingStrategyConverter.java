package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSendingStrategy;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSendingStategy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SendingStrategyConverter implements AttributeConverter<SendingStrategy, String> {
    @Override
    public String convertToDatabaseColumn(SendingStrategy sendingStrategy) {
        if (sendingStrategy instanceof MailSendingStrategy){
            return "MAIL";
        } else if (sendingStrategy instanceof TelegramSendingStategy){
            return "TELEGRAM";
        }
        else if (sendingStrategy instanceof WhatsAppSendingStrategy){
            return "WHATSAPP";
        }
        else {
            return null;
        }
    }

    @Override
    public SendingStrategy convertToEntityAttribute(String s) {
        if (s == null) {
            return null;  // o algún valor predeterminado si lo prefieres
        }

        return switch (s) {
            case "MAIL" -> new MailSendingStrategy(ServiceLocator.instanceOf(MimeMailSender.class));
            case "TELEGRAM" -> new TelegramSendingStategy(ServiceLocator.instanceOf(TelegramSender.class));
            case "WHATSAPP" -> new WhatsAppSendingStrategy(ServiceLocator.instanceOf(WhatsAppSender.class));
            default -> null;
        };
    }

}
