package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WhatsAppSender {
    public static final String ACCOUNT_SID = "AC38fe69ea9876a27e78e1603067856f85";
    public static final String AUTH_TOKEN = "015d4db3b8c1d319d6e1ee3aef56722a";

    public void enviarWhatsApp(String destinatario, String mensaje) {
        System.out.println("MENSAJE A WHATSAPP");
        
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
        new PhoneNumber("whatsapp:+5491140902002"),
        new PhoneNumber("whatsapp:+14155238886"),
        "Mensaje de alerta")
        .create();

        System.out.println(message.getSid());
        System.out.println(message.getErrorMessage());
    }
}
