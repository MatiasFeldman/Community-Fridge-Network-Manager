package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail;

import javax.mail.*;

public class EnviarMail{

    private MailSender mailSender;

    public EnviarMail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarMail(String destinatario, Mail mail) throws MessagingException {
        mailSender.enviarMail(destinatario, mail);
    }
}
