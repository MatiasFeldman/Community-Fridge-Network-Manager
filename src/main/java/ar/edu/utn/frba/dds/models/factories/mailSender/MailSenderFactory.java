package ar.edu.utn.frba.dds.models.factories.mailSender;

import ar.edu.utn.frba.dds.models.entities.helpers.mail.MailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.MimeMailSender;

public class MailSenderFactory {

    public static MailSender create(){
        return new MimeMailSender();
    }
}
