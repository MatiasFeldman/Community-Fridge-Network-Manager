package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail;

import lombok.NoArgsConstructor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@NoArgsConstructor
public class MimeMailSender implements MailSender {

    private Session newSession;

    @Override
    public void enviarMail(String destinatario, Mail mail) throws MessagingException {
        System.out.println("Enviando mail a " + destinatario);
        this.setupServerProperties();
        System.out.println("Sesion creada");
        MimeMessage message = this.createEmailMessage(newSession, destinatario, mail);
        System.out.println("Mensaje creado");
        this.sendEmail(message);

        System.out.println("Mail enviado a " + destinatario);

    }


    private void setupServerProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(props, null);

    }


    private MimeMessage createEmailMessage(Session session, String destinatario, Mail mail) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.setSubject(mail.getMotivo());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mail.getCuerpo(), "text/html");
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(mimeBodyPart);
        message.setContent(mimeMultipart);
        return message;
    }

    private void sendEmail(Message message) throws MessagingException {
        String nuestroMail = "lanaranjamecanicadds@gmail.com";
        String password = "xxzy rqbl fbji esol";
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, nuestroMail, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }
}
