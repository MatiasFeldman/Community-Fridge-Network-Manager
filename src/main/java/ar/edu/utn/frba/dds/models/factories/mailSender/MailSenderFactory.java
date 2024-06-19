package ar.edu.utn.frba.dds.models.factories.mailSender;

import ar.edu.utn.frba.dds.models.entities.helpers.mail.IMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.MimeMailSender;

public class MailSenderFactory {

    public static IMailSender create(){
        return new MimeMailSender();
    }
}
