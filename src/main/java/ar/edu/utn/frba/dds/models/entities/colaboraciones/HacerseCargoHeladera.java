package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

public class HacerseCargoHeladera implements ContribucionJuridica {
    private Heladera heladera;



    @Override
    public double calcularPuntaje() {
        if (heladera.isActiva()) {
            ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
            return constantes.getCteHeladeras() * heladera.mesesActiva();
        } else {
            return 0;
        }
    }


}
