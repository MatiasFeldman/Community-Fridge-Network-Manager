package ar.edu.utn.frba.dds.models.factories.mailSender;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;


public class MailSenderFactory {

    public static MailSender create(){
        return new MimeMailSender();
    }
}
