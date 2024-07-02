package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;

public interface MotivoNotificacion {
    boolean validar(Heladera heladera);
    Mensaje getMensaje();
}
