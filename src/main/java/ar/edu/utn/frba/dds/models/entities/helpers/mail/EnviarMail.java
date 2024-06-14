package ar.edu.utn.frba.dds.models.entities.helpers.mail;

import javax.mail.*;

public class EnviarMail{

    private String cuerpo;
    private String motivo;
    private IMailSender mailSender;

    public EnviarMail(String cuerpo, String motivo, IMailSender mailSender) {
        this.cuerpo = cuerpo;
        this.motivo = motivo;
        this.mailSender = mailSender;
    }

    public void enviarMail(String destinatario, Mail mail) throws MessagingException {
        mailSender.enviarMail(destinatario, mail);
    }
}
