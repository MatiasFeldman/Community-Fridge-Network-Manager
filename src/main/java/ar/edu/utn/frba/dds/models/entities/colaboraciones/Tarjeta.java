package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;



public interface Tarjeta {
   void usarEn(Heladera heladera);
   Long getId();

    Long getDuenioId();
}

