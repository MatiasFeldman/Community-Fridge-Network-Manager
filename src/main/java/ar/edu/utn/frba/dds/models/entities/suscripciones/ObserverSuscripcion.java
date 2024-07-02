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
    MotivoNotificacion motivo;
    SendingStrategy strategiaDeEnvio;

    public void verificarEvento(Heladera heladera){
        if (motivo.validar(heladera)){
            try {
                this.serNotificado(motivo.getMensaje());
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void serNotificado(Mensaje mensaje) throws MessagingException, IOException {
        strategiaDeEnvio.enviarMensaje(mensaje);
    }
}