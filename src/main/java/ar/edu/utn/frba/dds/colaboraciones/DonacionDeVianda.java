package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Vianda;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DonacionDeVianda implements ContribucionHumana{
    private Vianda vianda;
    @Override
    public void contribuir(){}

    @Override
    public double asignarPuntaje() {
        return 1.5;
    } //TODO: hacer bien las constantes multiplicativas
}
