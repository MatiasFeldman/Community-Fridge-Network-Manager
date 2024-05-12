package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;
import ar.edu.utn.frba.dds.helpers.ConstanteMultiplicativa;

import java.time.LocalDate;

public class DistribucionViandas implements ContribucionHumana{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private int cantidadViandas;
    private String motivo;
    private LocalDate fechaDistribucion;


    @Override
    public void contribuir() {
        heladeraOrigen.quitarViandas(cantidadViandas);
        heladeraDestino.agregarViandas(cantidadViandas);
        System.out.println("Distribucion de viandas realizada: se han distribuido " + cantidadViandas + " viandas.");
    }

    @Override
    public double asignarPuntaje() {
        return cantidadViandas * ConstanteMultiplicativa.CONSTANTE_VIANDAS_DISTRIBUIDAS;
    } //TODO: hacer bien las constantes multiplicativas
}
