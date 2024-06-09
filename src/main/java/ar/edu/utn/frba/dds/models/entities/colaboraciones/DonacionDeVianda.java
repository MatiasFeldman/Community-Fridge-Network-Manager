package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Vianda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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
