package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;

public interface MotivoNotificacion {
    public boolean validar(Heladera heladera);
    public Mensaje getMensaje();
}
