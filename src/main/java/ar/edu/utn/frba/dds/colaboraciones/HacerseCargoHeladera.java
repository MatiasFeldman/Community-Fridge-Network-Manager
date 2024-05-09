package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;

public class HacerseCargoHeladera implements ContribucionJuridica{
    private Heladera heladera;

    @Override
    public void contribuir() {

    }

    @Override
    public double asignarPuntaje() {
        return heladera.mesesActiva() * 5;
    } //TODO: hacer bien las constantes multiplicativas
}
