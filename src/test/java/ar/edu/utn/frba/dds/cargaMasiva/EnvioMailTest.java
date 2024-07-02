package ar.edu.utn.frba.dds.cargaMasiva;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.IMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.Mail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.mail.MessagingException;

public class EnvioMailTest {

    @Test
    public void enviarMailTest() throws MessagingException {
        IMailSender mailSenderImpostor = Mockito.mock(IMailSender.class);
        mailSenderImpostor.enviarMail("destinatario", new Mail("cuerpo", "motivo"));

        Mockito.verify(mailSenderImpostor, Mockito.times(1)).enviarMail(Mockito.any(), Mockito.any());
    }
}
