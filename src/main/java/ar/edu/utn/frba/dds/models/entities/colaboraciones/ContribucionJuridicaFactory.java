package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

public class ContribucionJuridicaFactory {

    public static HacerseCargoHeladera hacerseCargoHeladera(Heladera heladera, Juridica j){
        return HacerseCargoHeladera.of(heladera, j);
    }

    public static OfrecerProductoOServicio ofertar(Oferta oferta, Juridica j) {
        return OfrecerProductoOServicio.of(oferta, j);
    }

}
