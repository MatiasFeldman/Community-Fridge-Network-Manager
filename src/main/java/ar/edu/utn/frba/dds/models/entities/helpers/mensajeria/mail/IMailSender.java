package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail;

import javax.mail.MessagingException;

public interface IMailSender {
    public void enviarMail(String destinatario, Mail mail) throws MessagingException;
}
