package ar.edu.utn.frba.dds.models.entities.helpers.mail;

import javax.mail.MessagingException;

public interface MailSender {
    public void enviarMail(String destinatario, Mail mail) throws MessagingException;
}
