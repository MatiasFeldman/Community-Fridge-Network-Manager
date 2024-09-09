package ar.edu.utn.frba.dds.models.factories.sending_strategy;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSendingStategy;

public class SendingStrategyFactory {

    public static SendingStrategy create(String strategy){
        switch (strategy){
            case "TELEGRAM":
                return new TelegramSendingStategy(new TelegramSender());
            case "EMAIL":
                return new MailSendingStrategy(new MimeMailSender());
            default:
                return new MailSendingStrategy(new MimeMailSender());
        }
    }
}
