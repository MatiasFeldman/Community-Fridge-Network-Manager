package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

import java.util.UUID;

public interface Tarjeta {
   void usarEn(Heladera heladera);
   String getId();

    UUID getDuenioId();
}

