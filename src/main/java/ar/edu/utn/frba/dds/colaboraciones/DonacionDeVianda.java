package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Vianda;
import ar.edu.utn.frba.dds.helpers.ConstanteMultiplicativa;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DonacionDeVianda implements ContribucionHumana{
    private Vianda vianda;
    @Override
    public void contribuir(ColaboracionesRealizadas colaboracionesRealizadas){
        System.out.println("Donacion de vianda");
        colaboracionesRealizadas.agregarViandaDonada();
    }



}
