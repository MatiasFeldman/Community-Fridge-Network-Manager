package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.helpers.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.EnviarMail;
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
        } else {
            return null;
        }
    }

    @Override
    public SendingStrategy convertToEntityAttribute(String s) {
        if (s.equals("MAIL")){
            return new MailSendingStrategy(ServiceLocator.getMimeMailSender());
        } else if (s.equals("TELEGRAM")){
            return new TelegramSendingStategy(ServiceLocator.getTelegramSender());
        } else {
            return null;
        }
    }
}
