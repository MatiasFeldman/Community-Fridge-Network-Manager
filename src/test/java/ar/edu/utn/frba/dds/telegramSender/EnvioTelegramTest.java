package ar.edu.utn.frba.dds.telegramSender;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.ITelegramSender;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class EnvioTelegramTest {
    @Test
    public void enviarTelegramTest() throws IOException {
        ITelegramSender telegramSenderImpostor = Mockito.mock(ITelegramSender.class);
        telegramSenderImpostor.enviarTelegram("destinatario", "mensaje");

        Mockito.verify(telegramSenderImpostor, Mockito.times(1)).enviarTelegram(Mockito.any(), Mockito.any());
    }
}
