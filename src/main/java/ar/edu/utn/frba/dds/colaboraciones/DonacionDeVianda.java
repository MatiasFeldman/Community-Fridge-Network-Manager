package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Vianda;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DonacionDeVianda implements ContribucionHumana{
    private Vianda vianda;
    @Override
    public void contribuir(){
        System.out.println("Donacion de vianda");
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteViandasDonadas();
    }


}
