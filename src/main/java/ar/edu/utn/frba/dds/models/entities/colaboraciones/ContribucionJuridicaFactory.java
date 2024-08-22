package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

public class ContribucionJuridicaFactory {

    public static HacerseCargoHeladera hacerseCargoHeladera(Heladera heladera){
        return HacerseCargoHeladera.of(heladera);
    }
}
