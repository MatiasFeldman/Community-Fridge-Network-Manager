package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

@AllArgsConstructor
public class TelegramSender {

    public void enviarTelegram(String destinatario, String mensaje) throws IOException {
        String token = "TOKEN";
        String chatId = "CHAT_ID";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + chatId + "&text=" + mensaje)
                .build();
        client.newCall(request).execute();
    }
}
