package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class ObserverSuscripcion {
    MotivoNotificion motivo;

    public void verificarEvento(Heladera heladera){
        if (motivo.validar(heladera)){
            this.serNotificado(motivo.getMensaje());
        }
    }

    private void serNotificado(String mensaje) {
        // TODO: Implementar
    }
}