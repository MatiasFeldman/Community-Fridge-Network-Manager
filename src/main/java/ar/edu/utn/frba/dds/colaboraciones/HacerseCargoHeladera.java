package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;
import ar.edu.utn.frba.dds.helpers.ConstanteMultiplicativa;

public class HacerseCargoHeladera implements ContribucionJuridica{
    private Heladera heladera;

    @Override
    public void contribuir(ColaboracionesRealizadas colaboracionesRealizadas) {
        System.out.println("Hacerse cargo de heladera");
        colaboracionesRealizadas.agregarHeladera(heladera);
    }


}
