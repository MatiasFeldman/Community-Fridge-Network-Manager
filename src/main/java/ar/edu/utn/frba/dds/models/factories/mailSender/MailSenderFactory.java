package ar.edu.utn.frba.dds.models.factories.mailSender;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.IMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;

public class MailSenderFactory {

    public static IMailSender create(){
        return new MimeMailSender();
    }
}
