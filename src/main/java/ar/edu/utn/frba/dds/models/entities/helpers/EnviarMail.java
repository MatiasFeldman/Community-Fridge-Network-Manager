package ar.edu.utn.frba.dds.models.entities.helpers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EnviarMail {

    private String cuerpo;
    private String motivo;

    public EnviarMail(String cuerpo, String motivo) {
        this.cuerpo = cuerpo;
        this.motivo = motivo;
    }

    public void enviarMail(String mail, String cuerpo, String motivo) throws MessagingException {

        final String username = "nuestroMail@gmail.com";
        final String password = "nuestraClave";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("desdeEmail@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(mail)
        );
        message.setSubject(motivo);
        message.setText(cuerpo);

        Transport.send(message);

        System.out.println("Mail enviado a " + mail);

    }
}
