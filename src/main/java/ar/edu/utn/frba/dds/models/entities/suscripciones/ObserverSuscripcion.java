package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@Setter
public class ObserverSuscripcion {
    SendingStrategy strategiaDeEnvio;


    public void serNotificado(Mensaje mensaje) throws MessagingException, IOException {
        strategiaDeEnvio.enviarMensaje(mensaje);
    }
}