package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.Suscripcion;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class SuscripcionAHeladera {
    private ObserverSuscripcion observerSuscripcion;
    private Suscripcion suscripcion;


    @SneakyThrows
    public void notificar(Integer capActual, Integer cantActual) {
        if (suscripcion.verificarCondicion(capActual, cantActual)){
            observerSuscripcion.serNotificado(suscripcion.getMensaje());
        }
    }
}
