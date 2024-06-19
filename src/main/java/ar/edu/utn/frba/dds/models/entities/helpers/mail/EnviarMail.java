package ar.edu.utn.frba.dds.models.entities.helpers.mail;

import javax.mail.*;

public class EnviarMail{

    private IMailSender mailSender;

    public EnviarMail(IMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarMail(String destinatario, Mail mail) throws MessagingException {
        mailSender.enviarMail(destinatario, mail);
    }
}
