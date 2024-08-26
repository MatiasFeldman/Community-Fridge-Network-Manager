package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;

public interface Suscripcion {
    // falta implementar el metodo de getMensaje
    Mensaje getMensaje();

    boolean verificarCondicion(Integer capActual, Integer cantActual);
}
