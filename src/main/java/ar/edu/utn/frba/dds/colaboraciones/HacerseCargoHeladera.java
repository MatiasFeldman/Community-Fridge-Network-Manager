package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;

public class HacerseCargoHeladera implements ContribucionJuridica {
    private Heladera heladera;

    @Override
    public void contribuir() {
        System.out.println("Hacerse cargo de heladera");
    }

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
