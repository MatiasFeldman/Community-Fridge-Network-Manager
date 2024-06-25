package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

public interface MotivoNotificion {
    boolean validar(Heladera heladera);
    String getMensaje();
}
