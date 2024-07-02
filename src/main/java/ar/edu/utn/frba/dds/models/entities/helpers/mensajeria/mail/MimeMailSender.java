package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail;

import lombok.NoArgsConstructor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@NoArgsConstructor
public class MimeMailSender implements MailSender {
    private static final String username = "nuestroMail@gmail.com";
    private static final String password = "nuestraClave";
    private static final String desde_mail = "desdeEmail@gmail.com";
    private static final String smtp_host = "smtp.gmail.com";
    @Override
    public void enviarMail(String destinatario, Mail mail) throws MessagingException {

        Session session = createEmailSession();
        Message message = createEmailMessage(session, destinatario, mail);
        sendEmail(message);
        System.out.println("Mail enviado a " + destinatario);

    }
    private Session createEmailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host",smtp_host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    private Message createEmailMessage(Session session, String destinatario, Mail mail) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(desde_mail));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(destinatario)
        );
        message.setSubject(mail.getMotivo());
        message.setText(mail.getCuerpo());
        return message;
    }

    private void sendEmail(Message message) throws MessagingException {
        Transport.send(message);
    }
}
