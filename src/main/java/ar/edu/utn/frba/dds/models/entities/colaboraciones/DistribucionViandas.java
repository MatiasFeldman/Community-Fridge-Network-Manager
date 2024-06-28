package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DistribucionViandas implements ContribucionHumana{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidadViandas;
    private String motivo;
    private LocalDate fechaDistribucion;

    public DistribucionViandas(Integer cantViandas){
        this.cantidadViandas = cantViandas;
    }


    @Override
    public void contribuir() {
        heladeraOrigen.quitarViandas(cantidadViandas);
        heladeraDestino.agregarViandas(cantidadViandas); // directament q lo haga el controller. en el modelo registramos lo q pasó
        System.out.println("Distribucion de viandas realizada: se han distribuido " + cantidadViandas + " viandas.");
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteViandasDistribuidas() * cantidadViandas;
    }




}
